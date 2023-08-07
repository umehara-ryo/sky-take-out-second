package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.DishFlavor;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //1.dtoでデータベースから検索
        ShoppingCart shoppingCart = shoppingCartMapper.getByDTO(shoppingCartDTO);


        //2.あるなら　数を１増やす
        if (shoppingCart != null) {
            Integer number = shoppingCart.getNumber();
            shoppingCart.setNumber(number + 1);
            shoppingCartMapper.update(shoppingCart);
            return;
        }

        //3.ないなら　表に挿入
        //4.dishなら
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();

        shoppingCart = new ShoppingCart();

        //数に１を代入
        shoppingCart.setNumber(1);

        //ユーザーと時間を代入
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setUserId(BaseContext.getCurrentId());


        if(dishId != null){
            //料理の基本情報を代入
            DishVO dishVO = dishMapper.getById(dishId);
            shoppingCart.setImage(dishVO.getImage());
            shoppingCart.setName(dishVO.getName());
            shoppingCart.setDishId(dishId);


            //料理の味情報を代入
            String dishFlavor = shoppingCartDTO.getDishFlavor();
            shoppingCart.setDishFlavor(dishFlavor);

            //金額を代入
            shoppingCart.setAmount(dishVO.getPrice());



            //挿入
            shoppingCartMapper.insert(shoppingCart);

        }

        //5.setmealなら

        if(setmealId != null){
            //定食の基本情報を代入
            SetmealVO setmealVO = setmealMapper.getById(setmealId);
            shoppingCart.setImage(setmealVO.getImage());
            shoppingCart.setName(setmealVO.getName());
            shoppingCart.setSetmealId(setmealId);

            //金額を代入
            shoppingCart.setAmount(setmealVO.getPrice());

            //挿入
            shoppingCartMapper.insert(shoppingCart);

        }





    }
}
