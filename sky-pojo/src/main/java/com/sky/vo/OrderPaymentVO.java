package com.sky.vo;

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
public class OrderPaymentVO implements Serializable {

    private String nonceStr; //ランダム文字列
    private String paySign; //サイン
    private String timeStamp; //タイムスタンプ
    private String signType; //サインのアルゴル
    private String packageStr;
    //注文インターフェイスによって返される
    //prepay_idのIDパラメーター値を統一する

}
