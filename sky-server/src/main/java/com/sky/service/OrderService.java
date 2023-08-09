package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submit(OrdersDTO ordersDTO);
}
