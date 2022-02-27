package com.example.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.business.mapper.SecurityConfigMapper;
import com.example.business.model.SecurityConfig;
import com.example.business.service.SecurityConfigService;
import com.example.system.constant.CacheNameConstant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-20
 */
@Service
public class SecurityConfigServiceImp extends ServiceImpl<SecurityConfigMapper, SecurityConfig> implements SecurityConfigService {

    @Override
    public String getSecureKeyBySource(String source) {
        SecurityConfig securityConfig = getSecurityConfig(source);
        return securityConfig == null ? null : securityConfig.getSecureKey();
    }

    @Override
    public String getSignKeyBySource(String source) {
        SecurityConfig securityConfig = getSecurityConfig(source);
        return securityConfig == null ? null : securityConfig.getSignKey();
    }

    @Override
    @Cacheable(value = CacheNameConstant.DAY_3, keyGenerator = "myKeyGenerator")
    public SecurityConfig getSecurityConfig(String source) {
        QueryWrapper<SecurityConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source);
        return baseMapper.selectOne(wrapper);
    }
}
