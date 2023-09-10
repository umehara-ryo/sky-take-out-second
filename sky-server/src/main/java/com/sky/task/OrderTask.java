package com.sky.task;


/*
注文のステータスを定時に処理する
 */

import com.sky.constant.MessageConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /*
      タイムアウトしている注文を処理する
    */
    @Scheduled(cron = "0 * * * * * ? ") //分ごとに触発
    public void processTimeout() {
        log.info("タイムアウトしている注文を処理する：{}", LocalDateTime.now());

        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().plusMinutes(15));

        if (ordersList != null && ordersList.size() > 0){

            for (Orders orders : ordersList) {
                //注文情報変更
                orders.setCancelTime(LocalDateTime.now());
                orders.setCancelReason(MessageConstant.ORDER_TIMEOUT);
                orders.setStatus(Orders.CANCELLED);

                orderMapper.update(orders);
            }

        }



    }
}
