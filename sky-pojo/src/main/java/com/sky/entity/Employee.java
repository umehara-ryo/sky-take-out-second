package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String empCd;

    private String name;

    private String nationalityCd;

    private String nationalityName;

    private String genderCd;

    private String genderName;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 返还给前端的数据
    private LocalDate birthday;



}
