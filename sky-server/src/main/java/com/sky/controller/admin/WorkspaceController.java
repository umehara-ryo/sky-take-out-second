package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@Slf4j
@RestController
@RequestMapping("/admin/workspace")
@Api(tags = "ワークスペースに関わるインタフェース")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;


    @GetMapping("/businessData")
    @ApiOperation("本日のビジネスデータ")
    public Result<BusinessDataVO> businessData(){
        log.info("本日のビジネスデータ{}", LocalDate.now());
        BusinessDataVO businessDataVO = workspaceService.getBusinessData();

        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewSetmeals")
    @ApiOperation("定食一覧")
    public Result<SetmealOverViewVO> overviewSetmeals(){
        log.info("定食一覧");
        SetmealOverViewVO setmealOverViewVO = workspaceService.getOverviewSetmeals();

        return Result.success(setmealOverViewVO);
    }

    @GetMapping("/overviewDishes")
    @ApiOperation("定食一覧")
    public Result<DishOverViewVO> overviewDishes(){
        log.info("定食一覧");
        DishOverViewVO dishOverViewVO = workspaceService.getOverviewDishes();

        return Result.success(dishOverViewVO);
    }

}
