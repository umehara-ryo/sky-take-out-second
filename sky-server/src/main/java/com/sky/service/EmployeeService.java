package com.sky.service;

import com.sky.entity.Employee;


import java.util.List;

public interface EmployeeService {

    List<Employee> search();

    void add(Employee employee);

    List<Employee> find(String value);

    void update(Employee employee);

    void delete(String empCd);
}
