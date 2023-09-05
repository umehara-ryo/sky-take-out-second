package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    //主キー
    private Long id;

    //タイプ　1はメニューのカテゴリー 2は定食のカテゴリー
    private Integer type;

    //カテゴリー名
    private String name;

    //ソート
    private Integer sort;

}
