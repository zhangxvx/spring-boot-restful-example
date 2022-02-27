package com.example.business.controller;

import com.example.system.annotation.Auth;
import com.example.system.annotation.Secure;
import com.example.system.constant.CacheNameConstant;
import com.example.system.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/ok1")
    public Result<Object> ok1() {
        return Result.success();
    }

    @Auth
    @RequestMapping(value = "/ok2")
    public Result<Object> ok2() {
        return Result.success();
    }

    @Secure
    @RequestMapping(value = "/ok3")
    public String ok3(@NotBlank String msg) {
        return "ok3";
    }

    @Secure
    @RequestMapping(value = "/ok4")
    public Result<Object> ok4(@RequestBody Map<String, String> map) {
        return Result.success(map);
    }
}
