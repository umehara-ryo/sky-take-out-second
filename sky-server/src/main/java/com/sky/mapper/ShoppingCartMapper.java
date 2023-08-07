package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    void insert(ShoppingCart shoppingCart);

    ShoppingCart getByDTO(ShoppingCartDTO shoppingCartDTO);

    void update(ShoppingCart shoppingCart);

    List<ShoppingCart> list();

}
