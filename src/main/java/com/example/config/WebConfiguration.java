package com.example.config;

import com.example.filter.GlobalExceptionFilter;
import com.example.filter.JwtAuthenticationFilter;
import com.example.interceptor.GlobalHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<GlobalExceptionFilter> exceptionFilter() {
        GlobalExceptionFilter exceptionFilter = new GlobalExceptionFilter();
        FilterRegistrationBean<GlobalExceptionFilter> filterBean = new FilterRegistrationBean<>(exceptionFilter);
        filterBean.setUrlPatterns(Collections.singleton("/*"));
        return filterBean;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter();
        FilterRegistrationBean<JwtAuthenticationFilter> filterBean = new FilterRegistrationBean<>(jwtFilter);
        filterBean.setUrlPatterns(Collections.singleton("/pro/*"));
        return filterBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalHandlerInterceptor());
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }
}
