package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //微信ユーザーの唯一の識別
    private String openid;

    //名前
    private String name;

    //電話番号
    private String phone;

    //性別 0 女 1 男
    private String sex;

    //身分証明書番号
    private String idNumber;

    //プロフィール写真
    private String avatar;

    //登録時間
    private LocalDateTime createTime;
}
