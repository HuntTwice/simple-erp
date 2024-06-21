package com.zc.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zc.common.exception.CustomException;
import com.zc.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {




//    统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json串
    public Result customError(CustomException e){
        log.error("异常信息：{}",e.getMsg());
        return Result.error(e.getCode(),e.getMsg());
    }
}
