package com.example.system.interceptor;

import com.example.business.service.SecurityConfigService;
import com.example.system.annotation.Security;
import com.example.system.constant.SystemConstant;
import com.example.system.entity.SecurityMessage;
import com.example.system.exception.SecurityException;
import com.example.system.util.SecurityUtil;
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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * 请求解密
 */
@Slf4j
@RestControllerAdvice
public class SecurityRequestHandler implements RequestBodyAdvice {
    @Resource
    Map<Method, String> methodURIMap;

    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityConfigService securityConfigService;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getDeclaringClass().isAnnotationPresent(Security.class) || Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(Security.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Security security = parameter.getMethodAnnotation(Security.class);
        if (security == null) {
            security = parameter.getDeclaringClass().getAnnotation(Security.class);
        }
        String requestURI = methodURIMap.get(parameter.getMethod());
        String body = IOUtils.toString(inputMessage.getBody());

        if (security.decrypt()) {
            HttpHeaders headers = inputMessage.getHeaders();
            String source = headers.getFirst(SystemConstant.SOURCE);
            if (Objects.isNull(source)) {
                throw new SecurityException("来源渠道缺失");
            }
            SecurityMessage securityMessage = objectMapper.readValue(body, SecurityMessage.class);
            String encryptBody = securityMessage.getEncryptData();
            String signed = securityMessage.getSign();
            String decryptBody = SecurityUtil.decrypt(encryptBody, security.secure());
            assert decryptBody != null;
            boolean verify = securityConfigService.verify(decryptBody, signed, source, security.sign());
            if (verify) {
                log.debug("beforeBodyRead. requestURI:{}. decrypt:true. verify:true. secure:{}. sign:{}. body:{}. request:{}.", requestURI, security.secure(), security.sign(), securityMessage, decryptBody);
                return new DecryptHttpInputMessage(headers, IOUtils.toInputStream(decryptBody));
            }
            log.debug("beforeBodyRead. requestURI:{}. decrypt:true. verify:false. secure:{}. sign:{}. body:{}.", requestURI, security.secure(), security.sign(), securityMessage);
            throw new SecurityException("验签失败");
        }

        log.debug("beforeBodyRead. requestURI:{}. decrypt:false. body:{}.", requestURI, body);
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

