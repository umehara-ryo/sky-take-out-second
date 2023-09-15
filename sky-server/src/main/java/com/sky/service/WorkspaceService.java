package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;

public interface WorkspaceService {


    BusinessDataVO getBusinessData();

    SetmealOverViewVO getOverviewSetmeals();


    DishOverViewVO getOverviewDishes();

}
