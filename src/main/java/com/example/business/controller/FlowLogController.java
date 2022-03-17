package com.example.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.business.model.FlowLog;
import com.example.business.service.FlowLogService;
import com.example.system.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @RequestMapping("/add")
    public FlowLog add(@RequestParam String name) {
        FlowLog flowLog = new FlowLog();
        flowLog.setApplyId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        flowLog.setName(name);
        flowLogService.save(flowLog);
        return flowLog;
    }

    @RequestMapping("/list")
    public List<FlowLog> list(@RequestParam String name) {
        QueryWrapper<FlowLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return flowLogService.list(queryWrapper);
    }

    @RequestMapping("/page")
    public Page<FlowLog> page(Page<FlowLog> page, @RequestParam String name) {
        return flowLogService.pageList(page, name);
    }
}
