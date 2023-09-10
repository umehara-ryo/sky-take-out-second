package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //カテゴリーid
    private Long categoryId;

    //定食名
    private String name;

    //定食価格
    private BigDecimal price;

    //状態 0:無効 1:有効
    private Integer status;

    //デスクリプション
    private String description;

    //写真
    private String image;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
