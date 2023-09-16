package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private WorkspaceService workspaceService;


    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {

        List<LocalDate> dateList = getLocalDateList(begin, end);

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.getTurnOverByMap(map);
            turnover = turnover == null ? 0.0 : turnover;

            turnoverList.add(turnover);
            //日別で売上を算出

        }

        return new TurnoverReportVO(
                StringUtils.join(dateList, ","),
                StringUtils.join(turnoverList, ","));
    }

    private List<LocalDate> getLocalDateList(LocalDate begin, LocalDate end) {
        //beginデートからendデートまで毎日のデートを収納する
        List<LocalDate> dateList = new ArrayList<>();
        do {

            dateList.add(begin);
            begin = begin.plusDays(1);
            //当日の翌日の日付を算出する

        } while (!begin.equals(end));

        return dateList;
    }

    @Override
    public UserReportVO getUserReportVO(LocalDate begin, LocalDate end) {

        //localDateListを算出する
        List<LocalDate> dateList = getLocalDateList(begin, end);

        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            //総ユーザー数をクエリ
            Integer totalUser = userMapper.countByMap(map);

            map.put("begin", beginTime);
            //新規ユーザー数をクエリ
            Integer newUser = userMapper.countByMap(map);

            //nullの場合は、0を代入
            totalUser = totalUser == null ? 0 : totalUser;
            newUser = newUser == null ? 0 : newUser;

            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }


        return new UserReportVO(
                StringUtils.join(dateList, ","),
                StringUtils.join(totalUserList, ","),
                StringUtils.join(newUserList, ","));
    }

    @Override
    public OrderReportVO getOrderReportVO(LocalDate begin, LocalDate end) {
        //localDateListを算出する
        List<LocalDate> localDateList = getLocalDateList(begin, end);

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();


        for (LocalDate date : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            map.put("begin", beginTime);

            //日別注文数
            Integer orderCount = orderMapper.countByMap(map);

            //日別有効注文数
            map.put("status", Orders.COMPLETED);
            Integer validOrderCount = orderMapper.countByMap(map);

            orderCount = orderCount == null ? 0 : orderCount;
            validOrderCount = validOrderCount == null ? 0 : validOrderCount;

            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }

        //期間中の総注文数や有効注文を算出する
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        Map map = new HashMap();
        map.put("end", endTime);
        map.put("begin", beginTime);

        //期間中の総注文数
        Integer totalOrderCount = orderMapper.countByMap(map);

        //期間中の有効注文数
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.countByMap(map);

        totalOrderCount = totalOrderCount == null ? 0 : totalOrderCount;
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;

        //注文完成率を算出する
        Double orderCompletionRate = Double.valueOf(validOrderCount) / Double.valueOf(totalOrderCount);


        return OrderReportVO.builder()
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .dateList(StringUtils.join(localDateList, ","))
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10ReportVO(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        Integer status = Orders.COMPLETED;
        List<GoodsSalesDTO> list = orderMapper.getSalesTop10(beginTime, endTime, status);

        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();

        //商品名と販売量を分ける
        for (GoodsSalesDTO goodsSalesDTO : list) {
            nameList.add(goodsSalesDTO.getName());
            numberList.add(goodsSalesDTO.getNumber());
        }


        return new SalesTop10ReportVO(StringUtils.join(nameList, ","),
                StringUtils.join(numberList, ","));
    }

    @Override
    public void exportBusinessData(HttpServletResponse response) {
        //1.データベースからビジネスデータをクエリーー最近三十日間のデータ
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);

        BusinessDataVO businessData = workspaceService.getBusinessData(dateBegin, dateEnd);

        //2.POIによって、エクセルに書き写す
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("telemplate/ビジネスデータレポート.xlsx");

        try {
            XSSFWorkbook excel = new XSSFWorkbook(resourceAsStream);

            //データを埋め込む--期間
            XSSFSheet sheet = excel.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);
            row.getCell(1).setCellValue("期間：" + dateBegin + "～" + dateEnd);

            //データを埋め込む--データ一覧
            row = sheet.getRow(3);
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());

            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());

            //詳細データを埋め込む
            for (int i = 0; i < 30; i++) {
                LocalDate date = dateBegin.plusDays(i);
                BusinessDataVO detailData = workspaceService.getBusinessData(date, date);

                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(detailData.getTurnover());
                row.getCell(3).setCellValue(detailData.getValidOrderCount());
                row.getCell(4).setCellValue(detailData.getOrderCompletionRate());
                row.getCell(5).setCellValue(detailData.getUnitPrice());
                row.getCell(6).setCellValue(detailData.getNewUsers());

            }


            //3.エクスポートストリームでエクセルファイルをクライアントにダウンロード
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
