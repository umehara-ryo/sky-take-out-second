package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Map;

@Mapper
public interface UserMapper {

    User getByOpenId(String openId);

    void insert(User user);

    Integer countByMap(Map map);
}
