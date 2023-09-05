package com.sky.dto;

import com.sky.entity.SetmealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {

    private Long id;

    //カテゴリーid
    private Long categoryId;

    //定食名
    private String name;

    //定食価格
    private BigDecimal price;

    //ステータス 0:無効 1:有効
    private Integer status;

    //詳細情報
    private String description;

    //画像
    private String image;

    //料理と定食関連づけ
    private List<SetmealDish> setmealDishes = new ArrayList<>();

}
