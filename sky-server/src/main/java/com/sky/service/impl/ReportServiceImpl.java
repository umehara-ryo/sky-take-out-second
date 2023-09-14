package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
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

        List<LocalDate> dateList =  getLocalDateList(begin, end);

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
        List<LocalDate> dateList =  getLocalDateList(begin, end);

        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            Integer totalUser = userMapper.countByMap(map);

            map.put("begin", beginTime);
            Integer newUser = userMapper.countByMap(map);

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
}
