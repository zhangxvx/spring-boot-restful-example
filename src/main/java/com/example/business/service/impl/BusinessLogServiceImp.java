package com.example.business.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.business.mapper.BusinessLogMapper;
import com.example.business.model.BusinessLog;
import com.example.business.service.BusinessLogService;
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
public class BusinessLogServiceImp extends ServiceImpl<BusinessLogMapper, BusinessLog> implements BusinessLogService {

}
