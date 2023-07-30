package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/category")
@RestController
@Slf4j
@Api(tags = "カテゴリーに関するインタフェース")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("カテゴリー追加")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("カテゴリー追加{}",categoryDTO);
        categoryService.save(categoryDTO);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("カテゴリーのクエリ")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("カテゴリーのクエリ{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation("カテゴリー情報変更")
    public Result upadate(@RequestBody CategoryDTO categoryDTO){
        log.info("カテゴリー情報変更{}",categoryDTO);
        categoryService.update(categoryDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("ステータスのオンオフ")
    public Result onOrOff(@PathVariable Integer status, Long id){
        log.info("ステータスのオンオフ{}{}",status,id);
        categoryService.onOrOff(status,id);

        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("ステータスの削除")
    public Result delete(Long id){
        log.info("ステータスの削除{}",id);
        categoryService.delete(id);

        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("カテゴリータイプ別のクエリ")
    public Result<List<Category>> list(Integer type){
        log.info("カテゴリータイプ別のクエリ{}",type);
        List<Category> list = categoryService.list(type);

        return Result.success(list);
    }

}
