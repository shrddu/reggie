package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shr567
 * @create 2022/7/24 - 17:41
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
