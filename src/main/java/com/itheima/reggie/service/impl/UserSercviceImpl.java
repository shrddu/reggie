package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author shr567
 * @create 2022/7/29 - 20:46
 */
@Service
@Slf4j
public class UserSercviceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
