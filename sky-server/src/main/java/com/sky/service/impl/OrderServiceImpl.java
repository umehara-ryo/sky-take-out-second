package com.sky.service.impl;

import com.sky.dto.OrdersDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;




    @Override
    public OrderVO submit(OrdersDTO ordersDTO) {
        Orders orders = new Orders();
        OrderDetail orderDetail = new OrderDetail();

        List<ShoppingCart> shoppingCarts = shoppingCartService.list();
        //1.オーダーオブジェクトを作成し、numberを作る

        //2.それとuserIdと作成時間を代入

        //3.オーダー状態や支払い状態を設置する

        //4.表に挿入、idを返す

        //5.ショッピングカートの内容を注文詳細にコピー（orderIdの内容を忘れないように）

        //6.orderVOオブジェクトを作成、オーダーナンバーを代入
        OrderVO orderVO = new OrderVO();

        return orderVO;
    }
}
