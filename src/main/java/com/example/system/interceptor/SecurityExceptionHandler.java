package com.example.system.interceptor;

import com.example.business.service.SecurityConfigService;
import com.example.system.annotation.Security;
import com.example.system.constant.SystemConstant;
import com.example.system.entity.ResponseMessage;
import com.example.system.entity.SecurityMessage;
import com.example.system.exception.SecurityException;
import com.example.system.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 加密接口异常拦截，优先级在{@link GlobalExceptionHandler}之前
 */
@Slf4j
@Order(1000)
@RestControllerAdvice(annotations = Security.class)
public class SecurityExceptionHandler {
    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityConfigService securityConfigService;

    @ExceptionHandler({SecurityException.class})
    @ResponseStatus
    public Object se(HttpServletRequest request, HttpServletResponse response, Exception e, HandlerMethod handlerMethod) {
        return getSecurityResponse(request, e, handlerMethod, new ResponseMessage<>(HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus
    public Object re(HttpServletRequest request, HttpServletResponse response, Exception e, HandlerMethod handlerMethod) {
        return getSecurityResponse(request, e, handlerMethod, new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @SneakyThrows
    private Object getSecurityResponse(HttpServletRequest request, Exception e, HandlerMethod handlerMethod, ResponseMessage<Object> message) {
        Security security = handlerMethod.getMethodAnnotation(Security.class);
        if (security == null) {
            security = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Security.class);
        }
        String source = request.getHeader(SystemConstant.SOURCE);
        if (!Objects.isNull(source) && security.encrypt()) {
            String decryptBody = objectMapper.writeValueAsString(message);
            String encryptBody = securityConfigService.encrypt(decryptBody, source, security.secure());
            String sign = SecurityUtil.sign(decryptBody, security.sign());

            SecurityMessage securityMessage = new SecurityMessage(encryptBody, sign);
            log.error("requestURI:{}. exception:{}. encrypt:true. secure:{}. sign:{}. body:{}. response:{}.", request.getRequestURI(), e.getMessage(), security.secure(), security.sign(), message, securityMessage, e);
            return securityMessage;
        }

        log.error("requestURI:{}. exception:{}. encrypt:false. source:{}. body:{}.", request.getRequestURI(), e.getMessage(), source, message, e);
        return message;
    }
}

