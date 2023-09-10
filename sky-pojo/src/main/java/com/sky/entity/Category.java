package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //タイプ: 1料理カテゴリー 2定食カテゴリー
    private Integer type;

    //カテゴリー名
    private String name;

    //順番
    private Integer sort;

    //カテゴリーステータス 0無効 1表示有効
    private Integer status;

    //作成時間
    private LocalDateTime createTime;

    //更新時間
    private LocalDateTime updateTime;

    //作成者
    private Long createUser;

    //更新者
    private Long updateUser;
}
