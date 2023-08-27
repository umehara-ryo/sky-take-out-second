package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {


    List<Employee> getAll();

    void insert(Employee employee);

    List<Employee> findByValue(String value);

    void update(Employee employee);

    void delete(String empCd);
}
