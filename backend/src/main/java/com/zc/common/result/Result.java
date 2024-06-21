package com.zc.common.result;


import com.zc.common.enums.ResultCodeEnum;
import com.zc.common.enums.RoleEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private String code; //编码：200表示成功，其他表示失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = ResultCodeEnum.SUCCESS.code;
        result.msg = ResultCodeEnum.SUCCESS.msg;
        return result;
    }

    public static <T> Result<T> success(T t) {
        Result<T> result = new Result<T>();
        result.data = t;
        result.code = ResultCodeEnum.SUCCESS.code;
        result.msg = ResultCodeEnum.SUCCESS.msg;
        return result;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        Result result = new Result();
        result.msg = resultCodeEnum.msg;
        result.code = resultCodeEnum.code;
        return result;
    }


    public static Result error() {
        Result result = new Result();
        result.msg = ResultCodeEnum.SYSTEM_ERROR.msg;
        result.code = ResultCodeEnum.SYSTEM_ERROR.code;
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }
}

