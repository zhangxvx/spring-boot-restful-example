package com.example.system.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.business.service.SecurityConfigService;
import com.example.system.annotation.Secure;
import com.example.system.constant.SystemConstant;
import com.example.system.entity.SecretData;
import com.example.system.enums.ResultCode;
import com.example.system.exception.ResultException;
import com.example.system.util.RSAUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 请求解密
 */
@Slf4j
@RestControllerAdvice
public class SecureRequestHandler implements RequestBodyAdvice {
    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityConfigService securityConfigService;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getDeclaringClass().isAnnotationPresent(Secure.class) || Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(Secure.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Secure secure = parameter.getMethodAnnotation(Secure.class);
        if (secure == null) {
            secure = parameter.getDeclaringClass().getAnnotation(Secure.class);
        }

        if (secure.decrypt()) {
            String body = IOUtils.toString(inputMessage.getBody());
            HttpHeaders headers = inputMessage.getHeaders();
            String source = headers.getFirst(SystemConstant.SOURCE);
            if (StrUtil.isBlank(source)) {
                throw new ResultException(ResultCode.SOURCE_IS_BLANK);
            }

            String signKey = securityConfigService.getSignKeyBySource(source);
            if (StrUtil.isBlank(signKey)) {
                throw new ResultException(ResultCode.SOURCE_NOT_VALID);
            }

            SecretData secretData = objectMapper.readValue(body, SecretData.class);
            String encryptBody = secretData.getEncryptData();
            String signed = secretData.getSign();

            String decryptBody = RSAUtil.decryptByPrivateKey(encryptBody);
            boolean verify = RSAUtil.verify(decryptBody, signed, signKey);
            if (verify) {
                log.debug("beforeBodyRead. source:{}. body:{}. request:{}.", source, secretData, decryptBody);
                return new DecryptHttpInputMessage(headers, IOUtils.toInputStream(decryptBody));
            }
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}

@Getter
@AllArgsConstructor
class DecryptHttpInputMessage implements HttpInputMessage {
    HttpHeaders headers;
    InputStream body;
}

