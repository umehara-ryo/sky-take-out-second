package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    //アドレス帳id
    private Long addressBookId;
    //支払方法
    private int payMethod;
    //コメント
    private String remark;
    //配達予定時間
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    //配達状況 1すぐに送信 0特定の時間を選択する
    private Integer deliveryStatus;
    //カトラリーの数
    private Integer tablewareNumber;
    //カトラリーの数状況  1食事のサイズで提供 0特定の数量を選択
    private Integer tablewareStatus;
    //パッケージ料
    private Integer packAmount;
    //総金額
    private BigDecimal amount;
}
