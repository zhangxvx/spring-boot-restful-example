package com.example.system.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.system.annotation.Auth;
import com.example.system.constant.SystemConstant;
import com.example.system.enums.ResultCode;
import com.example.system.exception.ResultException;
import com.example.system.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethod().isAnnotationPresent(Auth.class) || handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(Auth.class)) {
            Auth annotation = handlerMethod.getMethodAnnotation(Auth.class);
            if (annotation == null) {
                annotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
            }

            String token = request.getHeader(SystemConstant.TOKEN);
            if (StrUtil.isBlank(token)) {
                throw new ResultException(ResultCode.TOKEN_IS_BLANK);
            }
            try {
                Claims claims = JwtTokenUtil.verify(token);
                log.info("uri:{}. claims:{}.", request.getRequestURI(), claims);
            } catch (Exception e) {
                throw new ResultException(ResultCode.TOKEN_NOT_VALID);
            }
        }
        return true;
    }
}
