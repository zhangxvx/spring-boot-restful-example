package com.example.business.controller;

import com.example.system.annotation.Authentication;
import com.example.system.annotation.Security;
import com.example.system.entity.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Security
@RestController
@RequestMapping
public class HelloController {
    @Authentication
    @RequestMapping(value = "/ok1")
    public ResponseMessage<Object> ok1() {
        return ResponseMessage.ok();
    }

    @Security
    @RequestMapping(value = "/ok2")
    public ResponseMessage<Object> ok2() {
        return ResponseMessage.ok();
    }

    @Security
    @RequestMapping(value = "/ok3")
    public ResponseMessage<Object> ok3(@RequestBody Map<String, String> map) {
        return ResponseMessage.ok(map);
    }

    @Security
    @Authentication
    @RequestMapping(value = "/ok4")
    public ResponseMessage<Object> ok4(@RequestBody Map<String, String> map) {
        return ResponseMessage.ok(map);
    }

    @RequestMapping(value = "/ex1")
    public ResponseMessage<Object> ex1() throws Exception {
        throw new Exception("测试异常");
    }

    @RequestMapping(value = "/ex2")
    public ResponseMessage<Object> ex2() throws RuntimeException {
        throw new RuntimeException("测试异常");
    }
}
