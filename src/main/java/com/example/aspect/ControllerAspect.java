package com.example.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(* com.example.controller..*.*(..))")
    private void controllerPointcut() {
    }


    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();

        log.info("requestURI:{}. method:{}. queryString:{}. args:{}.", requestURI, method, queryString, args);
        Object result = pjp.proceed();
        log.info("requestURI:{}. method:{}. result:{}.", requestURI, method, result);
        return result;
    }

    @Before("controllerPointcut()")
    public void before() {
    }

    @After("controllerPointcut()")
    public void after() {
    }

    @AfterReturning(value = "controllerPointcut()", returning = "result")
    public void afterReturning(Object result) {
    }

    @AfterThrowing(value = "controllerPointcut()", throwing = "throwable")
    public void afterThrowing(Throwable throwable) {
    }
}
