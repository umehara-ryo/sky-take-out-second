package com.sky.service.impl;

import com.sky.entity.LoginUser;
import com.sky.mapper.LoginMapper;
import com.sky.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public List<LoginUser> login(LoginUser loginUser) {
        return loginMapper.login(loginUser);
    }
}
