package com.zc.common.anno;

import com.zc.common.enums.OperationType;
import com.zc.common.enums.TitleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessTitle {
    // 业务模块
    TitleType titleType() default TitleType.OTHER;

}
