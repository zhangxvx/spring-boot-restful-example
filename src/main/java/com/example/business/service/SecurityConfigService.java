package com.example.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.business.model.SecurityConfig;
import com.example.system.enums.SecurityType;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-19
 */
public interface SecurityConfigService extends IService<SecurityConfig> {

    boolean verify(String data, String signed, String source, SecurityType sign);

    String encrypt(String data, String source, SecurityType secure);
}
