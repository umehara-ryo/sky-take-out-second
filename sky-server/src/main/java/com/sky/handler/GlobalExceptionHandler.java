package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

//@RestControllerAdvice //指定全局异常类
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler//指定要捕获的异常
    public Result exceptionHandler(BaseException ex){
        log.info("捕获全局异常{}",ex.getMessage());
        return  Result.error(ex.getMessage());
    }

    @ExceptionHandler//sql文の例外を捕獲する
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'naritaryo' for key 'employee.idx_username'

        String message = ex.getMessage();
        if (message.contains("Duplicate entry")){
            String username = message.split(" ")[2];
            String msg = username + MessageConstant.ALREADY_EXISTS_ERROR;
            return Result.error(msg);
        }
        else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
