package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetmealPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    //カテゴリーid
    private Integer categoryId;

    //ステータス 0:無効 1:有効
    private Integer status;

}
