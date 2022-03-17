package com.example.business.controller;

import com.example.system.controller.BaseController;
import com.example.system.entity.Result;
import com.example.system.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {
    @Resource
    private CacheManager cacheManager;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/clear")
    public Result<Object> clear(@RequestParam String cacheName) {
        log.info("clear cache cacheName:{}", cacheName);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            log.info("cacheManager:{}. clear cacheName:{}.", cacheManager.getClass().getSimpleName(), cacheName);
            cache.clear();
        }
        return Result.success();
    }

    @RequestMapping("/clearAll")
    public Result<Object> clearAll() {
        log.info("clear cache all");
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                log.info("cacheManager:{}. clear cacheName:{}.", cacheManager.getClass().getSimpleName(), cacheName);
                cache.clear();
            }
        });
        return Result.success();
    }

    @RequestMapping("/clearRedis")
    public Result<Object> clearRedis() {
        try {
            // 获取所有key
            Set<String> keys = stringRedisTemplate.keys("*");
            assert keys != null;
            // 迭代
            for (String key : keys) {
                log.info("clear key:{}.", key);
                // 循环删除
                stringRedisTemplate.delete(key);
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure(ResultCode.INTERNAL_ERROR);
        }
    }
}
