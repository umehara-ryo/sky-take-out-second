package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    PageResult getHistoryOrders(Integer page, Integer pageSize, Integer status);

    OrderVO getOrderDetail(Long id);

    void cancelOrder(Long id);

    void repetition(Long id);

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO countByStatus();

    void confirm(Orders orders);

    void rejection(Orders orders);

    void cancel(Orders orders);

    void delivery(Long id);

    void complete(Long id);

    void reminder(Long id);
}
