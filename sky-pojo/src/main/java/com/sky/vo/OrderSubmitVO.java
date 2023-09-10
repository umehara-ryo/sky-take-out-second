package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitVO implements Serializable {
    //注文id
    private Long id;
    //注文番号
    private String orderNumber;
    //注文金额
    private BigDecimal orderAmount;
    //注文時間
    private LocalDateTime orderTime;
}
