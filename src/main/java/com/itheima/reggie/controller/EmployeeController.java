package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author shr567
 * @create 2022/7/24 - 17:59
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @author shr
     * @date 2022/7/24 18:26
     * @param request
     * @param employee
     * @return R<Employee>
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
        if(one == null){
            return R.error("登录失败");
        }
        if(!one.getPassword().equals(password)){
            return R.error("登录失败");
        }

        if(one.getStatus() == 0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",one.getId());
        return R.success(one);
    }

    /**
     * 员工退出
     * @author shr
     * @date 2022/7/24 22:47
     * @param request
     * @return R<String>
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /*
    添加用户
     */
    @PostMapping
    public R<String> save(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) httpServletRequest.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) httpServletRequest.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return R.success("添加成功");
    }

    /*
    分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //分页构造器
        Page employeePage = new Page(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<Employee>();
        employeeLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        employeeLambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(employeePage,employeeLambdaQueryWrapper);
        return R.success(employeePage);
    }

    /*
    更新用户信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long) httpServletRequest.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    /*
    根据id查询用户
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id){
        Employee byId = employeeService.getById(id);
        return R.success(byId);
    }

    /*

     */

}
