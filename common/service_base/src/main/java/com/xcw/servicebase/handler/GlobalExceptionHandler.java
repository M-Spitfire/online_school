package com.xcw.servicebase.handler;

import com.xcw.servicebase.exception.MyException;
import com.xcw.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//指定对哪些异常生效,写Exception就是对所有异常生效
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("执行了全局异常处理..");
    }

    @ExceptionHandler(MyException.class)//指定对哪些异常生效,写Exception就是对所有异常生效
    @ResponseBody
    public R error(MyException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
