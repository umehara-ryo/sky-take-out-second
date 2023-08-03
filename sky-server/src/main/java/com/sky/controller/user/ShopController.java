package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api("ショップに関するインタフェース")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    private final static String KEY = "SHOP_STATUS";

    @GetMapping("status")
    @ApiOperation("営業状態を取得する")
    public Result<Integer> setStatus(){
        log.info("営業状態を取得する{}");
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);

        return Result.success(status);

    }
}
