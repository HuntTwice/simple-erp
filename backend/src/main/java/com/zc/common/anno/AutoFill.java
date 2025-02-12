package com.zc.common.anno;

import com.zc.common.enums.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于表示某个方法需要进行公共字段的填充处理
 */
@Target(ElementType.METHOD)  //指定注解加载方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    //指定数据库操作类型
    OperationType value();


}
