package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {

    List<Dish> getByCategoryId(Long id);

    @AutoFill(OperationType.INSERT)
    void save(Dish dish);
}
