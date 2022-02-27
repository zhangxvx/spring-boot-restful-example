package com.example.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.business.model.SecurityConfig;
import com.example.system.constant.CacheNameConstant;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-19
 */
public interface SecurityConfigService extends IService<SecurityConfig> {
    String getSecureKeyBySource(String source);

    String getSignKeyBySource(String source);

    SecurityConfig getSecurityConfig(String source);
}
