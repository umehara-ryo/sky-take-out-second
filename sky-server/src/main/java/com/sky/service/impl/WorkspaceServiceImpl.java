package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    public BusinessDataVO getBusinessData() {
        //本日新規ユーザー
        Map map = new HashMap();
        map.put("begin", LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        map.put("end", LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        Integer newUsers = userMapper.countByMap(map);

        //null値を0に
        newUsers = newUsers == null ? 0 : newUsers;


        //本日注文数
        Integer orderCount = orderMapper.countByMap(map);

        //本日有効注文数
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.countByMap(map);

        //null値を0に
        orderCount = orderCount == null ? 0 : orderCount;
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;

        //注文完成率
        Double orderCompletionRate = orderCount == 0 ? 0 : Double.valueOf(validOrderCount) / orderCount;

        //売上金額
        Double turnover = orderMapper.getTurnOverByMap(map);
        turnover = turnover == null ? 0 : turnover;

        //平均単価
        Double unitPrice = validOrderCount == 0 ? 0 : turnover / Double.valueOf(validOrderCount);


        return BusinessDataVO.builder()
                .newUsers(newUsers)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount)
                .build();
    }

    @Override
    public BusinessDataVO getBusinessData(LocalDate begin, LocalDate end) {
        //本日新規ユーザー
        Map map = new HashMap();
        map.put("begin", LocalDateTime.of(begin, LocalTime.MIN));
        map.put("end", LocalDateTime.of(end, LocalTime.MAX));

        Integer newUsers = userMapper.countByMap(map);

        //null値を0に
        newUsers = newUsers == null ? 0 : newUsers;


        //本日注文数
        Integer orderCount = orderMapper.countByMap(map);

        //本日有効注文数
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.countByMap(map);

        //null値を0に
        orderCount = orderCount == null ? 0 : orderCount;
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;

        //注文完成率
        Double orderCompletionRate = orderCount == 0 ? 0 : Double.valueOf(validOrderCount) / orderCount;

        //売上金額
        Double turnover = orderMapper.getTurnOverByMap(map);
        turnover = turnover == null ? 0 : turnover;

        //平均単価
        Double unitPrice = validOrderCount == 0 ? 0 : turnover / Double.valueOf(validOrderCount);


        return BusinessDataVO.builder()
                .newUsers(newUsers)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount)
                .build();
    }


    @Override
    public SetmealOverViewVO getOverviewSetmeals() {


        //販売中定食
        Integer status = 1;
        Integer sold = setmealMapper.countByStatus(status);

        //販売中止定食
        status = 0;
        Integer discontinued = setmealMapper.countByStatus(status);

        //ヌルをゼロにする
        sold = sold == null ? 0 : sold;
        discontinued = discontinued == null ? 0 : discontinued;

        return new SetmealOverViewVO(sold, discontinued);
    }

    @Override
    public DishOverViewVO getOverviewDishes() {

        //販売中料理
        Integer status = 1;
        Integer sold = dishMapper.countByStatus(status);

        //販売中止料理
        status = 0;
        Integer discontinued = dishMapper.countByStatus(status);

        //ヌルをゼロにする
        sold = sold == null ? 0 : sold;
        discontinued = discontinued == null ? 0 : discontinued;

        return new DishOverViewVO(sold, discontinued);

    }

    @Override
    public OrderOverViewVO getOverviewOrders() {


        Map map = new HashMap();
        map.put("begin", LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        map.put("end", LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        //本日注文数
        Integer allOrders = orderMapper.countByMap(map);

        //キャンセル注文数
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        //完了注文数
        map.put("status", Orders.CANCELLED);
        Integer completedOrders = orderMapper.countByMap(map);

        //発送待ち注文数
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        //受注待ち注文数
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.countByMap(map);

        //ヌルをゼロにする
        allOrders = allOrders == null ? 0 : allOrders;
        cancelledOrders = cancelledOrders == null ? 0 : cancelledOrders;
        completedOrders = completedOrders == null ? 0 : completedOrders;
        deliveredOrders = deliveredOrders == null ? 0 : deliveredOrders;
        waitingOrders = waitingOrders == null ? 0 : waitingOrders;


        return OrderOverViewVO
                .builder()
                .allOrders(allOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders)
                .waitingOrders(waitingOrders)
                .build();
    }
}
