package com.example.e_commerce.common_service.exception;

import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice   //统一异常处理的注解
public class GlobalExceptionHandler {
    //全局异常处理
    @ExceptionHandler(Exception.class)  //出现了指定的异常后调用方法
    @ResponseBody
    public Result error(){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result error(MyException e){
        return Result.build(null, e.getResultCodeEnum());
    }
}
