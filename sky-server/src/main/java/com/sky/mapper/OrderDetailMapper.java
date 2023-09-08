package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void add(List<OrderDetail> orderDetails);

    List<OrderDetail> getByOrderId(Long orderId);
}
