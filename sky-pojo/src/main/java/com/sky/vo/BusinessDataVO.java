package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *データの概要
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO implements Serializable {

    private Double turnover;//売上

    private Integer validOrderCount;//有效オーダー数

    private Double orderCompletionRate;//オーダー完成率

    private Double unitPrice;//平均オーダー価格

    private Integer newUsers;//新規ユーザー数

}
