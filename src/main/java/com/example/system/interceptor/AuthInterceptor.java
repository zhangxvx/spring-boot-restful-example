package com.example.system.interceptor;

import com.example.system.annotation.Authentication;
import com.example.system.annotation.Security;
import com.example.system.constant.SystemConstant;
import com.example.system.enums.AuthType;
import com.example.system.util.AuthUtil;
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
        if (handlerMethod.getMethod().isAnnotationPresent(Authentication.class) || handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(Authentication.class)) {
            Authentication annotation = handlerMethod.getMethodAnnotation(Authentication.class);
            if (annotation == null) {
                annotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Authentication.class);
            }
            AuthType authType = annotation.type();
            String token = request.getHeader(SystemConstant.TOKEN);
            boolean verify = AuthUtil.verify(token, authType);
            if (verify) {
                log.debug("authentication preHandle. requestURI:{}. auth:{}. verify:true.", request.getRequestURI(), authType);
                return true;
            }

            if (handlerMethod.getMethod().isAnnotationPresent(Security.class) || handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(Security.class)) {
                Security security = handlerMethod.getMethodAnnotation(Security.class);
                if (security == null) {
                    security = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Security.class);
                }
                request.setAttribute(SystemConstant.SECURITY, security);
                log.debug("authentication preHandle. requestURI:{}. auth:{}. verify:false. encrypt:{}. secure:{}. sign:{}.", request.getRequestURI(), authType, security.encrypt(), security.secure(), security.sign());
            } else {
                log.debug("authentication preHandle. requestURI:{}. auth:{}. verify:false. encrypt:false.", request.getRequestURI(), authType);
            }
            request.getRequestDispatcher(SystemConstant.UNAUTHORIZED).forward(request, response);
            return false;
        }
        log.debug("authentication preHandle. requestURI:{}. no authentication.", request.getRequestURI());
        return true;
    }
}
