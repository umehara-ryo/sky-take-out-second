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
public class SalesTop10ReportVO implements Serializable {

    //商品名のリスト、コマンで区切る、例えば、魚香肉丝,宫保鸡丁,水煮鱼
    private String nameList;

    //セールスのリスト、ｖマンで区切る、例如：260,215,200
    private String numberList;

}
