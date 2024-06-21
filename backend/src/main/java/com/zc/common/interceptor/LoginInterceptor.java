package com.zc.common.interceptor;


import com.zc.common.constants.JwtClaimsConstant;
import com.zc.common.enums.ResultCodeEnum;
import com.zc.common.exception.CustomException;
import com.zc.common.properties.JwtProperties;
import com.zc.utils.JwtUtil;
import com.zc.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;
//    mysql中的user表中，id是自动生成的，表中另一个字段create_user，在进行插入操作时，当插入的数据中create_user为空时，将id的值赋给create_user
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod))
            //当前拦截到的不是动态方法，直接放行
            return true;
        //获取token
        String token = request.getHeader(jwtProperties.getTokenName());

        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Integer userId = Integer.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            String username = claims.get(JwtClaimsConstant.USER_NAME).toString();
            String role = claims.get(JwtClaimsConstant.ROLE).toString();
            ThreadLocalUtil.set(userId);
            log.info("当前userid:{}", userId);
            log.info("当前username:{}", username);
            log.info("当前role:{}", role);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ThreadLocalUtil.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
