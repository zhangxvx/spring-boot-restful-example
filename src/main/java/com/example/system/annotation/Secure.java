package com.example.system.annotation;

import java.lang.annotation.*;

/**
 * 注解controller加密接口，注解类则该类所有方法都是加密请求/响应，方法注解优先于类注解
 * 若异常返回需要加密，则需注解对应的controller
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Secure {
    /**
     * 请求是否解密，默认是
     */
    boolean decrypt() default true;

    /**
     * 响应是否加密，默认是
     */
    boolean encrypt() default true;
}

