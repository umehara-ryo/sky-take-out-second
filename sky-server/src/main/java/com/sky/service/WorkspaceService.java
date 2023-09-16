package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDate;

public interface WorkspaceService {



    BusinessDataVO getBusinessData();

    BusinessDataVO getBusinessData(LocalDate begin, LocalDate end);

    SetmealOverViewVO getOverviewSetmeals();


    DishOverViewVO getOverviewDishes();

    OrderOverViewVO getOverviewOrders();

}
