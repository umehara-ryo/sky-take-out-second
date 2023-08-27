package com.sky.service.impl;

import com.sky.entity.Employee;
import com.sky.entity.Nationality;
import com.sky.mapper.EmployeeMapper;
import com.sky.mapper.NationalityMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private NationalityMapper nationalityMapper;

    @Override
    public List<Employee> search() {
        return employeeMapper.getAll();
    }

    @Override
    public void add(Employee employee) {
        //1.国籍はすでにデータベースにあるかどうかのを判明
        List<Nationality> nationalityList = nationalityMapper.getAll();
        for (Nationality nationality : nationalityList) {
            //あるなら、cdを代入
            if (employee.getNationalityName().equals(nationality.getNationalityName())){
                employee.setNationalityCd(nationality.getNationalityCd());
            }
        }

        //ないなら、国籍表に挿入、cdを返し代入
        if(employee.getNationalityCd()==null){
            Integer nCd = nationalityList.size() + 1;
            String newNCd = "00" +  nCd;
            employee.setNationalityCd(newNCd);
            nationalityMapper.insert(employee);
        }

        //2.表に挿入
        List<Employee> employees = employeeMapper.getAll();
        String empCd = "1000" + (employees.size()+11);
        employee.setEmpCd(empCd);
        employeeMapper.insert(employee);

    }

    @Override
    public List<Employee> find(String value) {
        return employeeMapper.findByValue(value);
    }
}
