package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        //1.setmealオブジェクトにカプセル化する、dish表に挿入
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        setmealMapper.save(setmeal);

        //2.主キーを返す、setmealDishesを取り出し、setmealIdを代入、setmeal_dish表に挿入
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmeal.getId());
            }
            setmealDishMapper.saveBatch(setmealDishes);

        }

        //3.トランザクションを起動

        //4.マッパーにAutoFillアノテーションをつける

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //1.pageHelperを起動
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        //2.setmeal表とcategory表とともに調べる、categoryNameというエイリアス名を設定


        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        //3.pageResultにカプセル化する
        long total = page.getTotal();
        List<SetmealVO> result = page.getResult();

        return new PageResult(total, result);
    }

    @Override
    public SetmealVO getById(Long id) {
        //1.setmeal表からsetmealを取り出し
        SetmealVO setmealVO = setmealMapper.getById(id);

        //2.setmealDish表からsetmealDishesを取り出し
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealID(id);


        //3.setmealDishesをsetmealVOにセットする
        setmealVO.setSetmealDishes(setmealDishes);


        return setmealVO;
    }

    @Override
    public void switchOnOff(Integer status, Long id) {


        //1.setmealidでsetmealdish表からデータを取得
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealID(id);
        //2.未販売であるdishがあるや否やを判明
        for (SetmealDish setmealDish : setmealDishes) {
            Long dishId = setmealDish.getDishId();
            DishVO dishVO = dishMapper.getById(dishId);
            if (dishVO.getStatus().equals(StatusConstant.DISABLE)) {
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }

        }
        setmealMapper.updateStatusById(status, id);
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        //1.setmeal表を更新する
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        //更新した定食は未販売となる
        setmealMapper.update(setmeal);

        //2.setmealIdでseatmeal_dishから削除
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());

        //3.setmealDishにsetmealIdをセットする、seatmeal_dishに挿入する

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            //実は要らない、丈夫さを高めるだけ
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealDTO.getId());
            }

            setmealDishMapper.saveBatch(setmealDishes);
        }


        //4.トランザクションをオンにする
        //5.マッパーにupdate
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //1.販売状態を判明
        for (Long id : ids) {
            SetmealVO setmealVO = setmealMapper.getById(id);
            if(setmealVO.getStatus().equals(StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //2.setmeal表から削除
        setmealMapper.delete(ids);

        //3.setmealDish表から削除
        for (Long setmealId : ids) {
            setmealDishMapper.deleteBySetmealId(setmealId);
        }


        //4.トランザクションをオンにする
    }
}
