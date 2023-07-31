package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "料理に関するインタフェース")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("料理の追加")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("料理の追加{}",dishDTO);

        dishService.save(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("料理のページクエリ")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        log.info("料理のページクエリ{}",dishPageQueryDTO);

        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


}
