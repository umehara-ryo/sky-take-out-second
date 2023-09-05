package com.sky.dto;

import com.sky.entity.DishFlavor;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO implements Serializable {

    private Long id;
    //料理名
    private String name;
    //料理カテゴリーのid
    private Long categoryId;
    //料理価格
    private BigDecimal price;
    //画像・イメージ
    private String image;
    //詳細
    private String description;
    //0 販売中止 1 販売中
    private Integer status;
    //味
    private List<DishFlavor> flavors = new ArrayList<>();

}
