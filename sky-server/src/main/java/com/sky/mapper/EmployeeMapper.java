package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    @AutoFill(OperationType.INSERT)
    void insert(Employee employee);


    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    void setStatusById(Integer status, Long id);


    Employee selectById(Long id);


    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);
}
