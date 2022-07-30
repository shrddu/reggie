package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shr567
 * @create 2022/7/29 - 20:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
