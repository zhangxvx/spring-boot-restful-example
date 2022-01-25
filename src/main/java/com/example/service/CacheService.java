package com.example.service;

import com.example.entity.Response;

public interface CacheService {

    Response<Object> clear(String cacheName);

    Response<Object> clearAll();
}
