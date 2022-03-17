package com.example.system.config;

import com.example.system.constant.CacheNameConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        Map<String, RedisCacheConfiguration> expires = new HashMap<>();
        expires.put(CacheNameConstant.SECOND_30, defaultConfig.entryTtl(Duration.ofSeconds(30)));
        expires.put(CacheNameConstant.MINUTE_15, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        expires.put(CacheNameConstant.MINUTE_30, defaultConfig.entryTtl(Duration.ofMinutes(30)));
        expires.put(CacheNameConstant.HOUR_1, defaultConfig.entryTtl(Duration.ofHours(1)));
        expires.put(CacheNameConstant.HOUR_6, defaultConfig.entryTtl(Duration.ofHours(6)));
        expires.put(CacheNameConstant.DAY_1, defaultConfig.entryTtl(Duration.ofDays(1)));
        expires.put(CacheNameConstant.DAY_3, defaultConfig.entryTtl(Duration.ofDays(3)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .initialCacheNames(expires.keySet())
                .withInitialCacheConfigurations(expires)
                .build();
    }
}
