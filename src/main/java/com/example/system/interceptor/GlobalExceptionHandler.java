package com.example.system.interceptor;

import com.example.system.entity.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局接口异常拦截器，优先级默认最低
 */
@Slf4j
@Order
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage<Object> notFound(HttpServletRequest request, Exception e) {
        ResponseMessage<Object> result = new ResponseMessage<>(HttpStatus.NOT_FOUND);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus
    public ResponseMessage<Object> re(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ResponseMessage<Object> result = new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus
    public ResponseMessage<Object> ex(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ResponseMessage<Object> result = new ResponseMessage<>(HttpStatus.BAD_GATEWAY);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }
}

