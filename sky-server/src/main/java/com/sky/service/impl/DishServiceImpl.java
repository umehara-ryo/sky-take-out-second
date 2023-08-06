package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        //1.dishDTOオブジェクトをdishオブジェクトにコピー
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //2.dishFlavorを取り出し
        List<DishFlavor> flavors = dishDTO.getFlavors();

        //3.dishをdish表に挿入、dish_flavor表に加える
        dishMapper.save(dish);

        //4.主キー戻りでdishIdをゲット、そして挿入
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dish.getId());
            }

            dishFlavorMapper.save(flavors);
        }


        //5．mapperにアノテーションを付ける
        //6.事務も忘れないように

    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //1.pageHelperを起動し
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        //2.pageを獲得
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> list = page.getResult();

        //3.flavorsを獲得、dishVOにセット
        for (DishVO dishVO : list) {
            List<DishFlavor> flavors = dishFlavorMapper.selectByDishId(dishVO.getId());
            dishVO.setFlavors(flavors);
        }

        //4.返す
        return new PageResult(total, list);
    }

    @Override
    public DishVO getById(Long id) {
        DishVO dishVO = dishMapper.getById(id);


        //3.flavorsを獲得、dishVOにセット
            List<DishFlavor> flavors = dishFlavorMapper.selectByDishId(dishVO.getId());
            dishVO.setFlavors(flavors);


        return dishVO;
    }

    @Override
    @Transactional
    public void onOff(Integer status, Long id) {
        dishMapper.setStatusById(status, id);

        //1.未販売状態にしたら、相応の定食も未販売にならなければ
        if(status.equals(StatusConstant.DISABLE)) {
            List<SetmealDish> setmealDishes = setmealDishMapper.getByDishId(id);
            if (setmealDishes != null && setmealDishes.size() > 0){
                for (SetmealDish setmealDish : setmealDishes) {
                    Long setmealId = setmealDish.getSetmealId();
                    setmealMapper.updateStatusById(StatusConstant.DISABLE,setmealId);
                }
            }
        }


        //2.トランザクションをオンにする
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {

        //1.dishオブジェクトを作成、dish表をupdateする
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        //2.dishIdを取り出し、dishIdでdish_flavor表から抹消する
        Long dishId = dishDTO.getId();
        dishFlavorMapper.deleteByDishId(dishId);

        //3.flavorsを取り出し、dish_flavor表に挿入
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.save(flavors);
        }

        //4.AutoFillアノテーションを付ける

    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {



        //1.販売状態を判明、オンの場合は削除できない
        for (Long id : ids) {
            DishVO dishVO = dishMapper.getById(id);
            if (dishVO.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //2.関連づけられる定食が存在すれば削除できません
        List<SetmealDish> setmealDishes = setmealDishMapper.getByDishIds(ids);
        if(setmealDishes == null || setmealDishes.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //3.idでdish表から削除
        dishMapper.deleteBatch(ids);


        //4.dishIdでdishflavor表から削除
        for (Long id : ids) {
            dishFlavorMapper.deleteByDishId(id);
        }


        //5.トランザクションをオンに

    }

    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        List<Dish> list = dishMapper.getByCategoryId(categoryId);

        return list;
    }

    @Override
    public List<DishVO> list(Long categoryId) {

        //1.dishesを取り出す
        List<Dish> dishes = getByCategoryId(categoryId);

        //2.dishVOListに挿入する
        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish dish : dishes) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish,dishVO);
            //dish_flavor表からflavorsを取り出し、dishVOに挿入する
            List<DishFlavor> flavors = dishFlavorMapper.selectByDishId(dishVO.getId());
            dishVO.setFlavors(flavors);

          dishVOList.add(dishVO);
        }

       return dishVOList;

    }


}
