package com.sky.controller.user;

import com.sky.dto.OrdersDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<OrderSubmitVO> submit(OrdersDTO ordersDTO){
        log.info("お客様が注文する{}",ordersDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersDTO);

        return Result.success(orderSubmitVO);
    }
}
