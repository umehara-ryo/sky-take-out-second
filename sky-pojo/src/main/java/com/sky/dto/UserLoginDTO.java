package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

//ユーザーログイン
@Data
public class UserLoginDTO implements Serializable {

    private String code;

}
