package com.example.business.controller;

import com.example.system.controller.BaseController;
import com.example.system.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {
    @Resource
    private CacheManager cacheManager;

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
}
