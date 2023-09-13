package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {

    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);
}
