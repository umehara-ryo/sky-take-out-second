package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    DishVO getById(Long id);

    void onOff(Integer status, Long id);

    void update(DishDTO dishDTO);

    void delete(List<Long> ids);

    List<Dish> getByCategoryId(Long categoryId);
}
