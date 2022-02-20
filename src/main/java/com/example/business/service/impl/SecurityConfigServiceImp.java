package com.example.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.business.mapper.SecurityConfigMapper;
import com.example.business.model.SecurityConfig;
import com.example.business.service.SecurityConfigService;
import com.example.system.enums.SecurityType;
import com.example.system.util.SecurityUtil;
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
    public boolean verify(String data, String signed, String source, SecurityType sign) {
        SecurityConfig securityConfig = baseMapper.selectOneBySource(source);
        String signKey = securityConfig.getSignKey();
        return SecurityUtil.verify(data, signKey, signed, sign);
    }

    @Override
    public String encrypt(String data, String source, SecurityType secure) {
        SecurityConfig securityConfig = baseMapper.selectOneBySource(source);
        String secureKey = securityConfig.getSecureKey();
        return SecurityUtil.encrypt(data, secureKey, secure);
    }
}
