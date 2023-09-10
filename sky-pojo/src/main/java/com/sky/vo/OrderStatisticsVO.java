package com.sky.vo;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class OrderStatisticsVO implements Serializable {
    //受注待ち数
    private Integer toBeConfirmed;

    //発送待ち数量
    private Integer confirmed;

    //配達中数量
    private Integer deliveryInProgress;
}
