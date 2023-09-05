package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    //従業員名前
    private String name;

    //ページ番号
    private int page;

    //ページあたりの情報数
    private int pageSize;

}
