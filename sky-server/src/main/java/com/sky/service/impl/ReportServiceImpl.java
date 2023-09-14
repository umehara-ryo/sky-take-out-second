package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import jdk.jshell.Snippet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
