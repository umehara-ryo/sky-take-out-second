package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "ショッピングカートに関わるインタフェース")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("ショッピングカートに追加")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("ショッピングカートに追加{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);

        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("ショッピングカートをクエリ")
    public Result<List<ShoppingCart>> list() {
        log.info("ショッピングカートをクエリ");
        List<ShoppingCart> shoppingCarts = shoppingCartService.list();
        return Result.success(shoppingCarts);
    }

    @PostMapping("/sub")
    @ApiOperation("ショッピングカートのなかの一つを削除")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("ショッピングカートのなかの一つを削除{}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);

        return Result.success();
    }
    @DeleteMapping("/clean")
    @ApiOperation("ショッピングカートの全部削除")
    public Result clean() {
        log.info("ショッピングカートの全部削除");
        shoppingCartService.clean();

        return Result.success();
    }
}
