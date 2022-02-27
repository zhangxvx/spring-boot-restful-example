package com.example.business.controller;

import com.example.system.constant.CacheNameConstant;
import com.example.system.entity.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/ok1")
    @Cacheable(value = CacheNameConstant.MINUTE_30, keyGenerator = "myKeyGenerator")
    public ResponseMessage<Object> ok1() {
        return ResponseMessage.ok();
    }
}
