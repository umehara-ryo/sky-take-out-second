package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

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
        if (flavors != null && flavors.size() > 0)
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dish.getId());
            }

        dishFlavorMapper.save(flavors);


        //5．mapperにアノテーションを付ける
        //6.事務も忘れないように

    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //1.pageHelperを起動し
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        //2.pageを獲得
       Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> list = page.getResult();

        //3.flavorsを獲得、listにセット
        for (DishVO dishVO : list) {
           List<DishFlavor> flavors =  dishFlavorMapper.selectByDishId(dishVO.getId());
           dishVO.setFlavors(flavors);
        }



        return new PageResult(total,list);
    }

    @Override
    public DishVO getById(Long id) {
        DishVO dishVO = dishMapper.getById(id);

        return dishVO;
    }

    @Override
    public void onOff(Integer status, Long id) {
        dishMapper.setStatusById(status,id);
    }
}
