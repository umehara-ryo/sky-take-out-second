package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel( description = "従業員のログインによって渡されるデータモデル")
public class EmployeeLoginDTO implements Serializable {

   @ApiModelProperty("ユーザーネーム")
    private String username;

    @ApiModelProperty("パスワード")
    private String password;

}
