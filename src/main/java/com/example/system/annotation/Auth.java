package com.example.system.annotation;


import java.lang.annotation.*;

/**
 * 注解controller身份认证接口，注解类则该类所有方法都需要身份认证，方法注解优先于类注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
