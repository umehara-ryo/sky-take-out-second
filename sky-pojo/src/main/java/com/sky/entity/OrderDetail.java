package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //名前
    private String name;

    //オーダーid
    private Long orderId;

    //料理id
    private Long dishId;

    //定食id
    private Long setmealId;

    //テイスト
    private String dishFlavor;

    //数量
    private Integer number;

    //金額
    private BigDecimal amount;

    //写真
    private String image;
}
