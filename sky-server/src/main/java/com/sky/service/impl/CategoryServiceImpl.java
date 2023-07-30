package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.CategoryTypeConstant;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.exception.CategoryException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


    @Override
    public void save(CategoryDTO categoryDTO) {
        //1.同じカテゴリーが存在するや否や判明
        String name = categoryDTO.getName();
        Category category = categoryMapper.getByName(name);

        //2.存在ならそれきり
        if (category != null) {
            throw new CategoryException(name + MessageConstant.ALREADY_EXISTS_ERROR);
        }

        //3.カテゴリーオブジェクトを作成し、ステータスをオフにセット
        //4．更新時間や作成時間など
        category = Category.builder().status(StatusConstant.DISABLE)
                .createTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(categoryDTO, category);

        //5．マッパーを呼び出し、データベースに挿入
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //1.ページヘルパーを起動させ
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        //2.マッパーを呼び出し調べる
        //3.pageオブジェクトを返す
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        //4.pageResultオブジェクトにラップする
        long total = page.getTotal();
        List<Category> result = page.getResult();

        return new PageResult(total, result);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        //1.categoryオブジェクトにラップする
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        //2.更新情報をセット,ステータスをオフに
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setStatus(StatusConstant.DISABLE);

        //3.マッパーを呼び出し
        categoryMapper.update(category);

    }

    @Override
    public void onOrOff(Integer status, Long id) {
        Category category = Category.builder().status(status).id(id).build();
        categoryMapper.update(category);

    }

    @Override
    public void delete(Long id) {
        //1.タイプやidを調べる
        Category category = categoryMapper.getById(id);
        Integer type = category.getType();
        //2.setmealまたはdish表から関連づける定食料理があるや否やを判明
        //3.あるなら削除出来ない、ないなら削除
        //TODO 全部削除でもありと思う
        if (type.equals(CategoryTypeConstant.DISH)) {
            List<Dish> dishes = dishMapper.getByCategoryId(id);
            if (dishes != null && dishes.size() > 0) {
                throw new CategoryException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
            }


        } else if (type.equals(CategoryTypeConstant.SETMEAL)) {
            List<Setmeal> setmeals = setmealMapper.getByCategoryId(id);
            if (setmeals != null && setmeals.size() > 0) {
                throw new CategoryException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
            }
        }else{
            throw new CategoryException(MessageConstant.UNKNOWN_ERROR);
        }
        //4.マッパーを呼び出し
        categoryMapper.delete(id);



    }

    @Override
    public List<Category> list(Integer type) {
        List<Category> categories = categoryMapper.list(type);
        return categories;
    }


}
