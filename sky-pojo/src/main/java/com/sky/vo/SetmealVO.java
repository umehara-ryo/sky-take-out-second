package com.sky.vo;

import com.sky.entity.SetmealDish;
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
public class SetmealVO implements Serializable {

    private Long id;

    //カテゴリーd
    private Long categoryId;

    //定食名
    private String name;

    //定食価格
    private BigDecimal price;

    //ステータス 0:無効 1:有効
    private Integer status;

    //デスクリプション
    private String description;

    //写真
    private String image;

    //更新時間
    private LocalDateTime updateTime;

    //カテゴリー名
    private String categoryName;

    //定食と料理の関連性
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
