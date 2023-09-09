package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "定食に関するインタフェース")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @PostMapping
    @ApiOperation("定食の追加業務")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("定食の追加業務{}",setmealDTO);
        setmealService.save(setmealDTO);

        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("定食のページクエリ")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("定食のページクエリ{}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);

        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation("idで検索")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("idで検索{}",id);
        SetmealVO setmealVO = setmealService.getById(id);

        return Result.success(setmealVO);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("販売状態の調整")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result switchOnOFF(@PathVariable Integer status, Long id){
        log.info("販売状態の調整{}{}",status,id);
        setmealService.switchOnOff(status,id);

        return Result.success();
    }

    @PutMapping
    @ApiOperation("定食情報更新")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("販売状態の調整{}",setmealDTO);
        setmealService.update(setmealDTO);

        return Result.success();

    }

    @DeleteMapping
    @ApiOperation("定食のバッチ削除")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    //todo　リストを受ける時、@RequestParamが必要
    public Result delete( @RequestParam("ids") List<Long> ids){
        log.info("定食のバッチ削除{}",ids);
        setmealService.delete(ids);

        return Result.success();

    }





}
