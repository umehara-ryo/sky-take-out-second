package com.sky.service.impl;

import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import com.sky.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public List<Employee> search() {
        return employeeMapper.getAll();
    }
}
