package com.example.business.controller;


import com.example.business.controller.base.BaseController;
import com.example.business.model.FlowLog;
import com.example.business.service.FlowLogService;
import com.example.system.entity.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-20
 */
@Slf4j
@RestController
@RequestMapping("/flow")
public class FlowLogController extends BaseController {

    @Resource
    FlowLogService flowLogService;

    @PostMapping("/add")
    public ResponseMessage<Object> add(@RequestParam String name) {
        FlowLog flowLog = new FlowLog();
        flowLog.setApplyId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        flowLog.setName(name);
        flowLogService.save(flowLog);
        return ResponseMessage.ok(flowLog);
    }
}
