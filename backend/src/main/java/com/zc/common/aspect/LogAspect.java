package com.zc.common.aspect;


import com.zc.common.anno.BusinessOperation;
import com.zc.common.anno.BusinessTitle;
import com.zc.common.constants.JwtClaimsConstant;
import com.zc.common.enums.OperationType;
import com.zc.common.enums.TitleType;
import com.zc.common.properties.JwtProperties;
import com.zc.common.result.Result;
import com.zc.mapper.OperationLogMapper;
import com.zc.pojo.entity.OperationLog;
import com.zc.utils.IPUtils;
import com.zc.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Aspect //AOP类
@Slf4j
public class LogAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;


    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private OperationLogMapper operationLogMapper;

    //定义切入点
    //* com.zc.controller.*.*(..)) 第一个*表示返回值是所有的类型 最后*表示所有的方法，(..)表示匹配方法里的所有参数类型
    @Pointcut("execution(* com.zc.controller.*.*(..)) && @annotation(com.zc.common.anno.BusinessOperation)")
    public void recordLogsPointCut() {
    }


    @Around("recordLogsPointCut()")
    public Object recordLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //获取连接点对象类上的注解
        BusinessTitle businessTitle = proceedingJoinPoint.getTarget().getClass().getAnnotation(BusinessTitle.class);

        //获取业务模块名称
        TitleType titleType = businessTitle.titleType();
        String title = titleType.getValue();

        //获取目标方法的签名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //通过签名获取方法上的注解对象
        BusinessOperation businessOperation = signature.getMethod().getAnnotation(BusinessOperation.class);
        //获取注解上的业务操作类型
        OperationType operationType = businessOperation.operationType();
        String operation = operationType.getValue();


        //todo 获取目标对象的逻辑不合理，需要修改
        //获取连接点运行时传入的参数
        Object[] args = proceedingJoinPoint.getArgs();

        //将参数转化为字符串
        String strArgs = Arrays.toString(args);
        log.info("args:{}",strArgs);

        String target = strArgs;
        if (target.length()>255)
            target = target.substring(0,255);

        //执行目标方法，并且将目标方法的执行结果转化为result
        Result result = (Result) proceedingJoinPoint.proceed();

        //todo msg获取也不合理，如果方法抛出异常，那么就执行失败了，但是日志还是需要记录的
        String msg = result.getMsg();


        //todo 用户注册和登录操作无法记录
//        if (operationType == OperationType.LOGIN && titleType == TitleType.USER) {
//            return;
//        }

        //从请求中获取token
        String token = httpServletRequest.getHeader(jwtProperties.getTokenName());
        //获取jwt签名加密时使用的秘钥
        String secretKey = jwtProperties.getSecretKey();
        //根据密钥对token进行解析，获取存入token的自定义内容
        Claims claims = JwtUtil.parseJWT(secretKey, token);

        //从token中获取此次操作的用户名和姓名
        String username = (String) claims.get(JwtClaimsConstant.USER_NAME);
        String name = (String) claims.get(JwtClaimsConstant.NAME);

        //获取操作的ip地址
        String ip = IPUtils.getIpAddr(httpServletRequest);

        //获取操作的时间
        LocalDateTime now = LocalDateTime.now();

        //封装为OperationLog对象
        OperationLog log = OperationLog.builder()
                .title(title)
                .operation(operation)
                .target(target)
                .msg(msg)
                .username(username)
                .name(name)
                .ip(ip)
                .time(now)
                .build();

        operationLogMapper.add(log);


        return result;
    }


}
