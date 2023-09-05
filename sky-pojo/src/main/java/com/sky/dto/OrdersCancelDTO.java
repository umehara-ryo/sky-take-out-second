package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersCancelDTO implements Serializable {

    private Long id;
    //オーダーキャンセル原因
    private String cancelReason;

}
