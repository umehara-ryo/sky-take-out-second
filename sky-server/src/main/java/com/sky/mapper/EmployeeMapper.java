package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    void insert(Employee employee);

    @Select("select * from sky_take_out.employee")
    Page<Employee> selectAll();

}
