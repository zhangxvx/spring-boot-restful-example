package com.example.business.controller;

import com.example.business.model.BusinessLog;
import com.example.system.annotation.Auth;
import com.example.system.annotation.Secure;
import com.example.system.constant.CacheNameConstant;
import com.example.system.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/hello")
public class HelloController {
    @Resource
    CacheManager cacheManager;

    @RequestMapping(value = "/ok1")
    // @Cacheable(value = CacheNameConstant.SECOND_30, keyGenerator = "myKeyGenerator")
    public Result<Object> ok1() {
        Cache cache = cacheManager.getCache(CacheNameConstant.SECOND_30);
        BusinessLog ok = cache.get("ok", BusinessLog.class);
        if (ok == null) {
            ok = new BusinessLog();
            ok.setName(LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
            cache.put("ok", ok);
        } else {
            ok.setName(LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
            cache.put("ok", ok);
        }
        return Result.success(ok);
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
