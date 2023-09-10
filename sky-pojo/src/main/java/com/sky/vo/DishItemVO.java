package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishItemVO implements Serializable {

    //料理名
    private String name;

    //数
    private Integer copies;

    //料理写真
    private String image;

    //料理デスクリプション
    private String description;
}
