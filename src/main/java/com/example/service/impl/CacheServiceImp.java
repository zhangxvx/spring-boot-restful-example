package com.example.service.impl;

import com.example.entity.Response;
import com.example.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CacheServiceImp implements CacheService {
    @Resource
    private CacheManager cacheManager;

    @Override
    public Response<Object> clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            log.info("cacheManager:{}. clear cacheName:{}.", cacheManager.getClass().getSimpleName(), cacheName);
            cache.clear();
        }
        return Response.success(null);
    }

    @Override
    public Response<Object> clearAll() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                log.info("cacheManager:{}. clear cacheName:{}.", cacheManager.getClass().getSimpleName(), cacheName);
                cache.clear();
            }
        });
        return Response.success(null);
    }
}
