package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author shr567
 * @create 2022/7/28 - 4:44
 */
public interface SetmealService extends IService<Setmeal> {
    /*
  新增套餐，同时需要保存套餐和菜品的关联关系
   */
    public void saveWithDish(SetmealDto setmealDto);


    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
