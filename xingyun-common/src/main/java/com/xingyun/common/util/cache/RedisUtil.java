/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.util.cache;

import com.xingyun.common.constant.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 *
 * @author yxuej
 * @since 2026-03-28
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param keys 键（可以传多个）
     */
    public void delete(String... keys) {
        redisTemplate.delete(List.of(keys));
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return get(key, String.class);
    }

    /**
     * 获取缓存（基本类型）
     *
     * @param key   键
     * @param clazz 值类型
     * @param <T>   泛型
     * @return 值
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        set(key, value, CacheConstants.ONE_MINUTE);
    }

    /**
     * 设置缓存并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     */
    public void set(String key, Object value, long time) {
        set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存并设置过期时间
     *
     * @param key      键
     * @param value    值
     * @param time     时间（秒）
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
}
