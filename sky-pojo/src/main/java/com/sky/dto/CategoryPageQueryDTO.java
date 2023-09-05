package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryPageQueryDTO implements Serializable {

    //ページ番号
    private int page;

    //ページあたりの情報数
    private int pageSize;

    //カテゴリー名
    private String name;

    //タイプ　1はメニューのカテゴリー 2は定食のカテゴリー
    private Integer type;

}
