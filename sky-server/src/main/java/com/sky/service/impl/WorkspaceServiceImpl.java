package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.entity.Setmeal;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
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
    public SetmealOverViewVO getOverviewSetmeals() {


        //販売中定食
        Integer status = 1;
        Integer sold = setmealMapper.countByStatus(status);

        //販売中止定食
        status = 0;
        Integer discontinued = setmealMapper.countByStatus(status);

        sold = sold == null ? 0 : sold;
        discontinued = discontinued == null ? 0 : discontinued;

        return new SetmealOverViewVO(sold,discontinued);
    }
}
