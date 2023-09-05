package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrdersPaymentDTO implements Serializable {
    //注文番号
    private String orderNumber;

    //支払方法
    private Integer payMethod;

}
