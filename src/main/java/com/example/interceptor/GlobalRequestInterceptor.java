package com.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
@Slf4j
public class GlobalRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        //获取请求body
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        String body = new String(bodyBytes, request.getCharacterEncoding());
        log.debug("uri:{}. queryString:{}. body:{}.", requestURI, queryString, body);
        return true;
    }
}
