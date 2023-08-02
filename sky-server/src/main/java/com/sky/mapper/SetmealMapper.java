package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {

    List<Setmeal> getByCategoryId(Long id);

    @AutoFill(OperationType.INSERT)
    void save(Setmeal setmeal);


}
