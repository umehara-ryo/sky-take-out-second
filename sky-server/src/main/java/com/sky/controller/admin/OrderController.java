package com.sky.controller.admin;

import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "お店側の注文に関わるインタフェース")
@RequestMapping("/admin/order")
@RestController("adminOrderController")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("注文検索")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("注文検索{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation("注文ステータスによる統計")
    public Result<OrderStatisticsVO> countByStatus() {
        log.info("注文ステータスによる統計");
        OrderStatisticsVO orderStatisticsVO = orderService.countByStatus();
        return Result.success(orderStatisticsVO);
    }

    @GetMapping("/details/{id}")
    @ApiOperation("注文詳細情報")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        log.info("注文詳細情報{}", id);
        OrderVO orderVO = orderService.getOrderDetail(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("受注確認")
    public Result confirm(@RequestBody Orders orders) {
        log.info("受注確認{}", orders);
        orderService.confirm(orders);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("受注確認")
    public Result rejection(@RequestBody Orders orders) {
        log.info("受注確認{}", orders);
        orderService.rejection(orders);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation(" 注文キャンセル")
    public Result cancel(@RequestBody Orders orders) {
        log.info("注文キャンセル{}", orders);
        orderService.cancel(orders);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("注文配達")
    public Result delivery(@PathVariable Long id) {
        log.info("注文配達{}", id);
        orderService.delivery(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("注文配達")
    public Result complete(@PathVariable Long id) {
        log.info("注文配達{}", id);
        orderService.complete(id);
        return Result.success();
    }

}
