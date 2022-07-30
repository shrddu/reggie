package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.jni.Address;

/**
 * @author shr567
 * @create 2022/7/30 - 1:26
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
