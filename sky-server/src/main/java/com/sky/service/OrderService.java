package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderVO submit(OrdersDTO ordersDTO);
}
