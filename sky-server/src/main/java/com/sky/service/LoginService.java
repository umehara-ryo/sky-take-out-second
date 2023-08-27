package com.sky.service;

import com.sky.entity.Employee;
import com.sky.entity.LoginUser;

import java.util.List;

public interface LoginService {
    List<LoginUser> login(LoginUser loginUser);


}
