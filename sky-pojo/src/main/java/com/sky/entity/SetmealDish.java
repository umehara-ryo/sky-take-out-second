package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐菜品关系
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //定食id
    private Long setmealId;

    //料理id
    private Long dishId;

    //料理名 （冗長フィールド）
    private String name;

    //料理価格
    private BigDecimal price;

    //料理の数
    private Integer copies;
}
