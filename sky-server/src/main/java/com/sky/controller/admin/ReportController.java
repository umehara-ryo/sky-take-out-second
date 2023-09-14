package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@Slf4j
@RequestMapping("/admin/report")
@Api(tags = "データ統計に関わるインタフェース")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/turnoverStatistics")
    @ApiOperation("売上統計")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("売上統計{}{}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatistics(begin, end);

        return Result.success(turnoverReportVO);
    }

    @GetMapping("/userStatistics")
    @ApiOperation("ユーザー統計")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("ユーザー{}{}", begin, end);
        UserReportVO userReportVO = reportService.getUserReportVO(begin, end);

        return Result.success(userReportVO);
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation("注文統計")
    public Result<OrderReportVO> orderStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("注文統計{}{}", begin, end);
        OrderReportVO orderReportVO = reportService.getOrderReportVO(begin, end);

        return Result.success(orderReportVO);
    }

    @GetMapping("/top10")
    @ApiOperation("売れ行きtop10統計")
    public Result<SalesTop10ReportVO> salesTop10ReportVO(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("売れ行きtop10統計{}{}", begin, end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.getSalesTop10ReportVO(begin, end);

        return Result.success(salesTop10ReportVO);
    }

}
