package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;



    @Transactional
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {


        //1.オーダーオブジェクトを作成し、numberを作る、金額を算出
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        //現時点のミリ秒の値を取得
        Long currentTimeMillis = System.currentTimeMillis();


        //それをnumberとして設置する
        String number = currentTimeMillis.toString();
        orders.setNumber(number);


        //2.userIdと作成時間を代入
        orders.setUserId(BaseContext.getCurrentId());
        orders.setOrderTime(LocalDateTime.now());

        //3.オーダー状態や支払い状態を設置する
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);

        //4.表に挿入、idを返す
        orderMapper.save(orders);

        //5.ショッピングカートの内容を注文詳細にコピー（orderIdの内容を忘れないように）
        List<ShoppingCart> shoppingCarts = shoppingCartService.list();
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart,orderDetail);

            //orderIdを挿入
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
        }

        orderDetailMapper.add(orderDetails);

        //6.ショッピングカートの内容を消す
        shoppingCartService.clean();


        //7.orderVOオブジェクトを作成、オーダーナンバーを代入
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .orderTime(orders.getOrderTime())
                .orderNumber(number)
                .orderAmount(orders.getAmount())
                .id(orders.getId()).build();


        return orderSubmitVO;
    }
}
