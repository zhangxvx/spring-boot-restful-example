package com.example.system.controller;


import com.example.business.service.SecurityConfigService;
import com.example.system.annotation.Security;
import com.example.system.constant.SystemConstant;
import com.example.system.entity.ResponseMessage;
import com.example.system.entity.SecurityMessage;
import com.example.system.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping
public class AuthController {
    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityConfigService securityConfigService;

    @SneakyThrows
    @RequestMapping(SystemConstant.UNAUTHORIZED)
    public Object unauthorizedSecurity(HttpServletRequest request) {
        ResponseMessage<Object> responseMessage = new ResponseMessage<>(HttpStatus.UNAUTHORIZED);
        Object attribute = request.getAttribute(SystemConstant.SECURITY);
        if (attribute == null) {
            return responseMessage;
        }
        Security security = (Security) attribute;
        String source = request.getHeader(SystemConstant.SOURCE);
        if (Objects.isNull(source)) {
            return responseMessage;
        }
        String decryptBody = objectMapper.writeValueAsString(responseMessage);
        String encryptBody = securityConfigService.encrypt(decryptBody, source, security.secure());
        String sign = SecurityUtil.sign(decryptBody, security.sign());
        return new SecurityMessage(encryptBody, sign);
    }
}
