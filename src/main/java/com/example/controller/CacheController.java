package com.example.controller;

import com.example.controller.base.BaseController;
import com.example.entity.Response;
import com.example.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {
    @Resource
    CacheService cacheService;

    @RequestMapping("/clear")
    public Response<Object> clear(@RequestParam String cacheName) {
        log.info("clear cache cacheName:{}", cacheName);
        return cacheService.clear(cacheName);
    }

    @RequestMapping("/clearAll")
    public Response<Object> clearAll() {
        log.info("clear cache all");
        return cacheService.clearAll();
    }
}
