package com.sky.controller.user;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags ="お客様側の注文に関わるインタフェース" )
@RequestMapping("/user/order")
@RestController("userOrderController")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/submit")
    @ApiOperation("お客様が注文する")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("お客様が注文する{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);

        return Result.success(orderSubmitVO);
    }

    @GetMapping("/historyOrders")
    @ApiOperation("注文履歴")
    public Result<PageResult> getHistoryOrders(Integer page, Integer pageSize, Integer status){
        log.info("注文履歴クエリ");
        PageResult pageResult = orderService.getHistoryOrders(page,pageSize,status);
        return Result.success(pageResult);

    }


}
