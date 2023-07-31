package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
