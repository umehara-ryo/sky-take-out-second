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
public class UserReportVO implements Serializable {

    //日付、コンマで区切る。例えば、2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    //ユーザー数、コンマで区切る。例えば、200,210,220
    private String totalUserList;

    //新規ユーザー、コンマで区切る。例えば、20,21,10
    private String newUserList;

}
