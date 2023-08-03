package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Api("ショップに関するインタフェース")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    private final static String KEY = "SHOP_STATUS";

    @PutMapping("/{status}")
    @ApiOperation("営業状態を設定する")
    public Result setStatus(@PathVariable Integer status){
        log.info("営業状態を設定する{}",status);
        redisTemplate.opsForValue().set(KEY,status);

        return Result.success();

    }
    @GetMapping("status")
    @ApiOperation("営業状態を取得する")
    public Result<Integer> setStatus(){
        log.info("営業状態を取得する{}");
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);

        return Result.success(status);

    }
}
