package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /*
      オーダー状態 1.支払い待ち 2受注待ち 3受注済み 4配達中 5完了済み 6キャンセル済み
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /*
     * 支払い状態 0未支払い 支払い済み 2返金
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    //オーダー番号
    private String number;

    //オーダー状態 1.支払い待ち 2受注待ち 3受注済み 4配達中 5完了済み 6キャンセル済み　7返金
    private Integer status;

    //注文ユーザーid
    private Long userId;

    //アドレスid
    private Long addressBookId;

    //注文時間
    private LocalDateTime orderTime;

    //支払い時間
    private LocalDateTime checkoutTime;

    //支払い方法 1WeChat，2Alipay
    private Integer payMethod;

    //支払い状態 0未支払い 1支払い済み 2返金
    private Integer payStatus;

    //実際の取引金額
    private BigDecimal amount;

    //コメント
    private String remark;

    //ユーザー名
    private String userName;

    //電話番号
    private String phone;

    //アドレス
    private String address;

    //受荷主
    private String consignee;

    //キャンセル原因
    private String cancelReason;

    //拒否原因
    private String rejectionReason;

    //キャンセル時間
    private LocalDateTime cancelTime;

    //到着予定時刻
    private LocalDateTime estimatedDeliveryTime;

    //配達状態  1今すぐ発送  0時間を選択する
    private Integer deliveryStatus;

    //到着時間
    private LocalDateTime deliveryTime;

    //パッケージ料
    private int packAmount;

    //食器数
    private int tablewareNumber;

    //食器数状態  1食料による  0数を選択
    private Integer tablewareStatus;
}
