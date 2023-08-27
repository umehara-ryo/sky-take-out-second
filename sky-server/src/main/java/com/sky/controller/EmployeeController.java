package com.sky.controller;

import com.sky.entity.Employee;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "従業員に関するインタフェース")
@RequestMapping("/employee")
@Slf4j
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("情報クエリ")
    @GetMapping("/search")
    public Result<List<Employee>> search() {
        log.info("全員クエリ");
        List<Employee>  employeeList = employeeService.search();
        return Result.success(employeeList);
    }
}
