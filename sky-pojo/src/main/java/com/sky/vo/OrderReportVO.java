package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    //日付　コンマ「,」で区切る。例えば、2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    //本日注文数、コンマ「,」で区切る。例えば、260,210,215
    private String orderCountList;

    //本日有效注文数、コンマ「,」で区切る。例えば、20,21,10
    private String validOrderCountList;

    //総注文数
    private Integer totalOrderCount;

    //有效注文数
    private Integer validOrderCount;

    //注文完成率
    private Double orderCompletionRate;

}
