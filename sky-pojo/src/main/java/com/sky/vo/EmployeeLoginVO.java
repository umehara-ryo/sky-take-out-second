package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data//getter,setter,toString,equals
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "従業員ログインする時戻るデータフォーマット")
public class EmployeeLoginVO implements Serializable {

    @ApiModelProperty("主キー")
    private Long id;

    @ApiModelProperty("ユーザー名")
    private String userName;

    @ApiModelProperty("名前")
    private String name;

    @ApiModelProperty("jwtトークン")
    private String token;

}
