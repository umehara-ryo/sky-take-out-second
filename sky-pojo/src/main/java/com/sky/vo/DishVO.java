package com.sky.vo;

import com.sky.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishVO implements Serializable {

    private Long id;
    //料理名
    private String name;
    //料理カテゴリーid
    private Long categoryId;
    //料理価格
    private BigDecimal price;
    //写真
    private String image;
    //デスクリプション
    private String description;
    //0 販売中止 1 販売中
    private Integer status;
    //更新時間
    private LocalDateTime updateTime;
    //カテゴリー名称
    private String categoryName;
    //菜品に関係づけられるテイスト
    private List<DishFlavor> flavors = new ArrayList<>();

    //private Integer copies;
}
