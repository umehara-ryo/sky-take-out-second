package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordEditDTO implements Serializable {

    //従業員id
    private Long empId;

    //前のパスワード
    private String oldPassword;

    //新しきパスワード
    private String newPassword;

}
