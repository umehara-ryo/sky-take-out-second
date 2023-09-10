package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //名前
    private String name;

    //ユーザーid
    private Long userId;

    //料理id
    private Long dishId;

    //定食id
    private Long setmealId;

    //テイスト
    private String dishFlavor;

    //数
    private Integer number;

    //金額
    private BigDecimal amount;

    //写真
    private String image;

    private LocalDateTime createTime;
}
