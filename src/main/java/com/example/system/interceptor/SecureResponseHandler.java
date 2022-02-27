package com.example.system.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.business.service.SecurityConfigService;
import com.example.system.annotation.Secure;
import com.example.system.constant.SystemConstant;
import com.example.system.entity.Result;
import com.example.system.entity.SecretData;
import com.example.system.enums.ResultCode;
import com.example.system.exception.ResultException;
import com.example.system.util.RSAUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Objects;

/**
 * 响应加密
 */
@Slf4j
@RestControllerAdvice
public class SecureResponseHandler implements ResponseBodyAdvice<Object> {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityConfigService securityConfigService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getDeclaringClass().isAnnotationPresent(Secure.class) || Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(Secure.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Secure secure = returnType.getMethodAnnotation(Secure.class);
        if (secure == null) {
            secure = returnType.getDeclaringClass().getAnnotation(Secure.class);
        }

        if (secure.encrypt()) {
            HttpHeaders headers = request.getHeaders();
            String source = headers.getFirst(SystemConstant.SOURCE);
            if (StrUtil.isBlank(source)) {
                throw new ResultException(ResultCode.SOURCE_IS_BLANK);
            }

            if (body instanceof Result) {
                Result<Object> result = (Result<Object>) body;
                return getResult(body, request, source, result);
            }
            if (body instanceof String) {
                Result<Object> result = objectMapper.readValue(body.toString(), Result.class);
                return objectMapper.writeValueAsString(getResult(body, request, source, result));
            }
        }
        return body;
    }

    private Object getResult(Object body, ServerHttpRequest request, String source, Result<Object> result) throws JsonProcessingException {
        if (result.getStatus().equals(ResultCode.SUCCESS.getStatus())) {
            String secureKey = securityConfigService.getSecureKeyBySource(source);
            if (StrUtil.isBlank(secureKey)) {
                throw new ResultException(ResultCode.SOURCE_NOT_VALID);
            }

            String decryptBody = objectMapper.writeValueAsString(result.getData());
            String encryptBody = RSAUtil.encryptByPublicKey(decryptBody, secureKey);
            String sign = RSAUtil.sign(decryptBody);
            SecretData secretData = new SecretData(encryptBody, sign);
            result.setData(secretData);
            log.debug("beforeBodyWrite. requestURI:{}. source:{}. body:{}. response:{}.", request.getURI(), source, body, result);
            return result;
        }
        return result;
    }
}

