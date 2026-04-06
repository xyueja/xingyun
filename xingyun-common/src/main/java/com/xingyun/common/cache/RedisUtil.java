/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.cache;

import com.xingyun.common.constant.CacheConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 *
 * @author yxuej
 * @since 2026-03-28
 */
@Component
public class RedisUtil {
    private static RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public static boolean hasKey(String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存key
     * @return boolean
     */
    public static boolean delete(final String key) {
        return redisUtil.redisTemplate.delete(key);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return Objects.toString(redisUtil.redisTemplate.opsForValue().get(key), "");
    }

    /**
     * 获取缓存（基本类型）
     *
     * @param key 键
     * @param <T> 泛型
     * @return 值
     */
    @Nullable
    public static <T> T getObj(String key) {
        Object value = redisUtil.redisTemplate.opsForValue().get(key);
        return value == null ? null : (T) value;
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        set(key, value, CacheConstants.ONE_MINUTE);
    }

    /**
     * 设置缓存并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     */
    public static void set(String key, Object value, long time) {
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
    public static void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisUtil.redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
}
