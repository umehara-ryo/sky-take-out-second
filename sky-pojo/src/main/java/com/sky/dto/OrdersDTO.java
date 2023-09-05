package com.sky.dto;

import com.sky.entity.OrderDetail;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO implements Serializable {

    private Long id;

    //注文番号
    private String number;

    //注文ステータス　1支払い待ち 2待受注 3受注済み 4配達中 5完了済み
    private Integer status;

    //ユーザーid
    private Long userId;

    //アドレスid
    private Long addressBookId;

    //注文時間
    private LocalDateTime orderTime;

    //支払い時間
    private LocalDateTime checkoutTime;

    //支払い方法 1.WeChat　2.Alipay
    private Integer payMethod;

    //受け取る金額
    private BigDecimal amount;

    //コメント
    private String remark;

    //ユーザーネーム
    private String userName;

    //電話番号
    private String phone;

    //アドレス
    private String address;

    //受取人
    private String consignee;

    private List<OrderDetail> orderDetails;

}
