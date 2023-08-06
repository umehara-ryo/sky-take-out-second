package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "ユーザーがカテゴリーをご覧になる")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        log.info("ユーザーがカテゴリーをご覧になる{}",type);
        List<Category> list = categoryService.list(type);


        return Result.success(list);

    }
}
