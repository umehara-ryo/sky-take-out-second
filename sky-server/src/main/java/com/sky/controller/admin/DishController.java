package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @ApiOperation("idでクエリ")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("idでクエリ{}",id);
        DishVO dishVO = dishService.getById(id);

        return Result.success(dishVO);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("販売状態オンオフ")
    public Result onOff(@PathVariable Integer status,Long id){
        log.info("販売状態オンオフ");
        dishService.onOff(status,id);

        return Result.success();
    }

    @PutMapping
    @ApiOperation("料理の情報変更")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("料理の情報変更{}",dishDTO);
        dishService.update(dishDTO);

        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("料理の情報削除")
    public Result delete(@RequestParam("ids") List<Long> ids){
        log.info("料理の情報削除{}",ids);
        dishService.delete(ids);

        return Result.success();
    }





}
