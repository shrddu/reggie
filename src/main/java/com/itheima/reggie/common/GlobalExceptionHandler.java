package com.itheima.reggie.common;

import com.itheima.reggie.controller.EmployeeController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author shr567
 * @create 2022/7/25 - 21:42
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler{
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")){
            String[] s = message.split(" ");
            String msg = s[2]+"已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    };

    @ExceptionHandler(CustomerException.class)
    public R<String> customerExceptionHandler(CustomerException ex){
        String message = ex.getMessage();
        return R.error(message);
    };
}
