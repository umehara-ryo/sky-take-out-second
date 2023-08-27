package com.sky.mapper;

import com.sky.entity.Employee;
import com.sky.entity.Nationality;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NationalityMapper {

    List<Nationality> getAll();

    void insert(Employee employee);
}
