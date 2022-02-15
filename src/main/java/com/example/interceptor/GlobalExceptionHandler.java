package com.example.interceptor;

import com.example.entity.Response;
import com.example.enums.ResEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<Object> notFound(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("{}::{}::{}", response.getStatus(), request.getRequestURI(), e.getMessage());
        return Response.fail(ResEnum.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus
    public Response<Object> re(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("{}::{}::{}", response.getStatus(), request.getRequestURI(), e.getMessage(), e);
        return Response.fail(ResEnum.RUNTIME_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus
    public Response<Object> ex(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("{}::{}::{}", response.getStatus(), request.getRequestURI(), e.getMessage(), e);
        return Response.fail(ResEnum.ERROR);
    }
}

