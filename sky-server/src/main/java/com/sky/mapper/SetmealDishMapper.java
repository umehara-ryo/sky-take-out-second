package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    void saveBatch(List<SetmealDish> setmealDishes);

    List<SetmealDish> getBySetmealID(Long setmealId);

    void deleteBySetmealId(Long setmealId);

    List<SetmealDish> getByDishIds(List<Long> dishIds);


   List<SetmealDish>  getByDishId(Long dishId);

}
