package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {

    private Long id;
    //注文ステータス　1支払い待ち 2待受注 3受注済み 4配達中 5完了済み 6 キャンセル済み 7返金済み
    private Integer status;

}
