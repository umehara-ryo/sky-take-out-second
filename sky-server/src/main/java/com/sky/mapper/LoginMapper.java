package com.sky.mapper;

import com.sky.entity.LoginUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {

    List<LoginUser> login(LoginUser loginUser);

}
