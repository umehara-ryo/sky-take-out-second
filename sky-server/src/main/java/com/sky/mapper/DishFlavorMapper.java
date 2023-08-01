package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void save(List<DishFlavor> flavors);

    List<DishFlavor> selectByDishId(Long DishId);

    void deleteByDishId(Long dishId);
}
