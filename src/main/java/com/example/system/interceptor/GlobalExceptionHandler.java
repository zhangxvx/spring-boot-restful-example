package com.example.system.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.system.entity.Result;
import com.example.system.enums.ResultCode;
import com.example.system.exception.ResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 全局接口异常拦截器，优先级默认最低
 */
@Slf4j
@Order
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Object> notFound(HttpServletRequest request, Exception e) {
        Result<Object> result = Result.failure(ResultCode.NOT_FOUND);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus
    public Result<Object> bindException(HttpServletRequest request, BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(t -> errorMap.put(t.getField(), t.getDefaultMessage()));
        Result<Object> result = Result.failure(ResultCode.PARAM_NOT_VALID, errorMap);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus
    public Result<Object> bindException(HttpServletRequest request, ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            log.info("{}", path);
            String[] pathArr = StrUtil.splitToArray(path.toString(), ".");
            errorMap.put(pathArr[1], violation.getMessage());
        }

        Result<Object> result = Result.failure(ResultCode.PARAM_NOT_VALID, errorMap);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }

    @ExceptionHandler({ResultException.class})
    @ResponseStatus
    public Result<Object> resultException(HttpServletRequest request, ResultException e) {
        Result<Object> result = Result.failure(e.getResultCode());
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus
    public Result<Object> ex(HttpServletRequest request, Exception e) {
        Result<Object> result = Result.failure(ResultCode.INTERNAL_ERROR);
        log.error("requestURI:{}. exception:{}. response:{}.", request.getRequestURI(), e.getMessage(), result, e);
        return result;
    }
}

