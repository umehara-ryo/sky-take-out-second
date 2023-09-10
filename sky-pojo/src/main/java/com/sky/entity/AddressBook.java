package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //ユーザーid
    private Long userId;

    //受荷主
    private String consignee;

    //電話番号
    private String phone;

    //性別 0 女 1 男
    private String sex;

    //都道府県区画番号
    private String provinceCode;

    //都道府県名
    private String provinceName;

    //市区画番号
    private String cityCode;

    //市名
    private String cityName;

    //区の区画番号
    private String districtCode;

    //区名
    private String districtName;

    //詳細アドレス
    private String detail;

    //ラベル
    private String label;

    //デフォルトか否か 0いえ 1はい
    private Integer isDefault;
}
