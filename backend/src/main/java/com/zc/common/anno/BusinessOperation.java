package com.zc.common.anno;

import com.zc.common.enums.OperationType;
import com.zc.common.enums.TitleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessOperation {


    // 操作类型
    OperationType operationType() default OperationType.OTHER;
}
