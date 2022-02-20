package com.example.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.business.mapper.FlowLogMapper;
import com.example.business.model.FlowLog;
import com.example.business.service.FlowLogService;
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
public class FlowLogServiceImp extends ServiceImpl<FlowLogMapper, FlowLog> implements FlowLogService {

}
