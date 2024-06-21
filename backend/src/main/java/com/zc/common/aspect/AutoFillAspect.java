package com.zc.common.aspect;

import com.zc.common.anno.AutoFill;
import com.zc.common.constants.AutoFillConstant;
import com.zc.common.enums.OperationType;
import com.zc.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充的处理逻辑
 */

@Component
@Aspect
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.zc.mapper.*.*(..)) && @annotation(com.zc.common.anno.AutoFill)")
    public void autoFillPointCut() {
    }


    /**
     * 前置通知，在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段的自动填充:...");

        //获取当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType operationType = autoFill.value(); //获得操作类型

        //获取当前被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) return;//如果没有参数就返回
        Object entity = args[0];

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Integer id = null;
        //用户注册时，并没有生成id，如果是注册的请求就不需要填充，我们去service方法里处理
        id = ThreadLocalUtil.get();

        //根据当前的不同操作类型，为对应的属性通过反射来赋值
        if (operationType == OperationType.INSERT) {
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Integer.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Integer.class);

            //通过反射为对象属性赋值
            setCreateTime.invoke(entity, now);
            setUpdateTime.invoke(entity, now);
            setCreateUser.invoke(entity, id);
            setUpdateUser.invoke(entity, id);

        } else if (operationType == OperationType.UPDATE) {
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Integer.class);
            setUpdateTime.invoke(entity, now);
            setUpdateUser.invoke(entity, id);
        }


    }
}
