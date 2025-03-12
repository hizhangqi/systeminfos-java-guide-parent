package com.systeminfos.springboot3demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    @Qualifier("clusterRedisTemplate")
    private RedisTemplate<String, Object> clusterRedisTemplate;
    @Autowired
    @Qualifier("sentinelRedisTemplate")
    private RedisTemplate<String, Object> sentinelRedisTemplate;

    // 保存数据到 Redis
    public void set(String key, Object value, long timeout) {
        sentinelRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 从 Redis 获取数据
    public Object get(String key) {
        return clusterRedisTemplate.opsForValue().get(key);
    }

    // 删除 Redis 数据
    public boolean delete(String key) {
        return Boolean.TRUE.equals(clusterRedisTemplate.delete(key));
    }
}