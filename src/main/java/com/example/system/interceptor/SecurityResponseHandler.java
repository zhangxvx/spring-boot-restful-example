package com.example.system.interceptor;

import com.example.business.service.SecurityConfigService;
import com.example.system.annotation.Security;
import com.example.system.constant.SystemConstant;
import com.example.system.entity.SecurityMessage;
import com.example.system.exception.SecurityException;
import com.example.system.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * 响应加密
 */
@Slf4j
@RestControllerAdvice
public class SecurityResponseHandler implements ResponseBodyAdvice<Object> {
    @Resource
    Map<Method, String> methodURIMap;

    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityConfigService securityConfigService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getDeclaringClass().isAnnotationPresent(Security.class) || Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(Security.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Security security = returnType.getMethodAnnotation(Security.class);
        if (security == null) {
            security = returnType.getDeclaringClass().getAnnotation(Security.class);
        }
        String requestURI = methodURIMap.get(returnType.getMethod());

        if (security.encrypt()) {
            HttpHeaders headers = request.getHeaders();
            String source = headers.getFirst(SystemConstant.SOURCE);
            if (Objects.isNull(source)) {
                log.debug("beforeBodyWrite. requestURI:{}. encrypt:true. body:{}. source is null", requestURI, body);
                throw new SecurityException("来源渠道缺失");
            }
            String decryptBody = objectMapper.writeValueAsString(body);
            String encryptBody = securityConfigService.encrypt(decryptBody, source, security.secure());
            String sign = SecurityUtil.sign(decryptBody, security.sign());
            SecurityMessage securityMessage = new SecurityMessage(encryptBody, sign);
            log.debug("beforeBodyWrite. requestURI:{}. encrypt:true. secure:{}. sign:{}. body:{}. response:{}.", requestURI, security.secure(), security.sign(), body, securityMessage);
            return securityMessage;
        }
        log.debug("beforeBodyWrite. requestURI:{}. encrypt:false. body:{}.", requestURI, body);
        return body;
    }
}

