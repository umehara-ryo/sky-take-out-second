package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {

    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    UserReportVO getUserReportVO(LocalDate begin, LocalDate end);

    OrderReportVO getOrderReportVO(LocalDate begin, LocalDate end);
}
