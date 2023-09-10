package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * 注文概要データ
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverViewVO implements Serializable {
    //受注待ち数
    private Integer waitingOrders;

    //配達待ち数
    private Integer deliveredOrders;

    //完了済み数
    private Integer completedOrders;

    //キャンセル済み数
    private Integer cancelledOrders;

    //全ての注文
    private Integer allOrders;
}
