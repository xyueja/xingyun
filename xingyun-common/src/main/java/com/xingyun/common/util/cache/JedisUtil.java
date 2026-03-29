/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.util.cache;

import com.xingyun.common.constant.BaseConstants;
import com.xingyun.common.constant.CacheConstants;
import com.xingyun.common.util.json.JsonUtil;
import com.xingyun.common.util.json.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类（Jedis实现）
 *
 * @author yxuej
 * @since 2026-03-28
 */
@Component
public class JedisUtil {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return jedisCluster.exists(key);
    }

    /**
     * 删除缓存
     *
     * @param keys 键（可以传多个）
     * @return 删除的个数
     */
    public long delete(String... keys) {
        return jedisCluster.del(keys);
    }


    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return jedisCluster.get(key);
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
        String value = get(key);
        if (value == null) {
            return null;
        }
        return JsonUtil.toObject(value, clazz);
    }

    /**
     * 获取缓存（泛型对象）
     *
     * @param key  键
     * @param type 值类型
     * @param <T>  泛型
     * @return 值
     */
    public <T> T get(String key, Type<T> type) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return JsonUtil.toObject(value, type);
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
     * @param time  时间（秒），time > 0 则设置过期时间
     */
    public void set(String key, Object value, long time) {
        String jsonString = value instanceof String ? (String) value : JsonUtil.toString(value);
        jedisCluster.setex(key, time, jsonString);
    }

    // ============================== Hash操作 ==============================

    /**
     * 获取Hash中的值
     *
     * @param key   键
     * @param field 项
     * @return 值
     */
    public String hGet(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    /**
     * 获取Hash中的值（泛型）
     *
     * @param key   键
     * @param field 项
     * @param clazz 类型
     * @param <T>   泛型
     * @return 值
     */
    public <T> T hGet(String key, String field, Class<T> clazz) {
        String value = hGet(key, field);
        if (value == null) {
            return null;
        }
        return JsonUtil.toObject(value, clazz);
    }

    /**
     * 获取Hash所有键值对
     *
     * @param key 键
     * @return Map
     */
    public Map<String, String> hGetAll(String key) {
        return jedisCluster.hgetAll(key);
    }

    /**
     * 获取Hash所有键值对（泛型）
     *
     * @param key   键
     * @param clazz 值类型
     * @param <T>   泛型
     * @return Map
     */
    public <T> Map<String, T> hGetAll(String key, Class<T> clazz) {
        Map<String, String> map = jedisCluster.hgetAll(key);
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), JsonUtil.toObject(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * 设置Hash
     *
     * @param key 键
     * @param map 值
     */
    public void hSetAll(String key, Map<String, Object> map) {
        Map<String, String> strMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            strMap.put(entry.getKey(), value instanceof String ? (String) value : JsonUtil.toString(value));
        }
        jedisCluster.hset(key, strMap);
    }

    /**
     * 设置Hash中的值
     *
     * @param key   键
     * @param field 项
     * @param value 值
     */
    public void hSet(String key, String field, Object value) {
        String strValue = value instanceof String ? (String) value : JsonUtil.toString(value);
        jedisCluster.hset(key, field, strValue);
    }

    /**
     * 删除Hash中的值
     *
     * @param key    键
     * @param fields 项（可以多个）
     * @return 删除的个数
     */
    public long hDelete(String key, String... fields) {
        return jedisCluster.hdel(key, fields);
    }

    /**
     * 判断Hash中是否有该项
     *
     * @param key   键
     * @param field 项
     * @return 是否存在
     */
    public boolean hHasKey(String key, String field) {
        return jedisCluster.hexists(key, field);
    }

    /**
     * Hash递增
     *
     * @param key   键
     * @param field 项
     * @param delta 递增因子
     * @return 递增后的值
     */
    public double hIncrement(String key, String field, double delta) {
        return jedisCluster.hincrByFloat(key, field, delta);
    }

    // ============================== Set操作 ==============================

    /**
     * 获取Set中的所有值
     *
     * @param key 键
     * @return Set
     */
    public Set<String> sGet(String key) {
        return jedisCluster.smembers(key);
    }

    /**
     * 获取Set中的所有值（泛型）
     *
     * @param key   键
     * @param clazz 元素类型
     * @param <T>   泛型
     * @return Set
     */
    public <T> Set<T> sGet(String key, Class<T> clazz) {
        Set<String> members = jedisCluster.smembers(key);
        Set<T> result = new HashSet<>();
        for (String member : members) {
            result.add(JsonUtil.toObject(member, clazz));
        }
        return result;
    }

    /**
     * 判断Set中是否有该值
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    public boolean sHasKey(String key, String value) {
        return jedisCluster.sismember(key, value);
    }

    /**
     * 设置Set
     *
     * @param key    键
     * @param values 值（可以多个）
     * @return 成功个数
     */
    public long sSet(String key, String... values) {
        return jedisCluster.sadd(key, values);
    }

    /**
     * 设置Set（对象）
     *
     * @param key    键
     * @param values 值（可以多个）
     * @return 成功个数
     */
    public long sSetObject(String key, Object... values) {
        String[] strValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strValues[i] = values[i] instanceof String ? (String) values[i] : JsonUtil.toString(values[i]);
        }
        return jedisCluster.sadd(key, strValues);
    }

    /**
     * 删除Set中的值
     *
     * @param key    键
     * @param values 值（可以多个）
     * @return 成功个数
     */
    public long sRemove(String key, String... values) {
        return jedisCluster.srem(key, values);
    }

    /**
     * 获取Set大小
     *
     * @param key 键
     * @return 大小
     */
    public long sSize(String key) {
        return jedisCluster.scard(key);
    }

    // ============================== List操作 ==============================

    /**
     * 获取List中的值
     *
     * @param key   键
     * @param start 开始
     * @param end   结束（0到-1代表所有值）
     * @return List
     */
    public List<String> lGet(String key, long start, long end) {
        return jedisCluster.lrange(key, start, end);
    }

    /**
     * 获取List中的值（泛型）
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @param clazz 元素类型
     * @param <T>   泛型
     * @return List
     */
    public <T> List<T> lGet(String key, long start, long end, Class<T> clazz) {
        List<String> list = jedisCluster.lrange(key, start, end);
        List<T> result = new ArrayList<>();
        for (String item : list) {
            result.add(JsonUtil.toObject(item, clazz));
        }
        return result;
    }

    /**
     * 获取List长度
     *
     * @param key 键
     * @return 长度
     */
    public long lGetListSize(String key) {
        return jedisCluster.llen(key);
    }

    /**
     * 通过索引获取List中的值
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    public String lGetIndex(String key, long index) {
        return jedisCluster.lindex(key, index);
    }

    /**
     * 通过索引获取List中的值（泛型）
     *
     * @param key   键
     * @param index 索引
     * @param clazz 类型
     * @param <T>   泛型
     * @return 值
     */
    public <T> T lGetIndex(String key, long index, Class<T> clazz) {
        String value = jedisCluster.lindex(key, index);
        if (value == null) {
            return null;
        }
        return JsonUtil.toObject(value, clazz);
    }

    /**
     * 设置List（右侧推入）
     *
     * @param key   键
     * @param value 值
     */
    public void lSet(String key, Object value) {
        String strValue = value instanceof String ? (String) value : JsonUtil.toString(value);
        jedisCluster.rpush(key, strValue);
    }

    /**
     * 设置List（批量右侧推入）
     *
     * @param key    键
     * @param values 值
     */
    public void lSetAll(String key, List<Object> values) {
        String[] strValues = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            strValues[i] = value instanceof String ? (String) value : JsonUtil.toString(value);
        }
        jedisCluster.rpush(key, strValues);
    }

    /**
     * 根据索引修改List中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public void lUpdateIndex(String key, long index, Object value) {
        String strValue = value instanceof String ? (String) value : JsonUtil.toString(value);
        jedisCluster.lset(key, index, strValue);
    }

    /**
     * 移除List中的值
     *
     * @param key   键
     * @param count 移除个数
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, String value) {
        return jedisCluster.lrem(key, count, value);
    }

    /**
     * 左侧弹出
     *
     * @param key 键
     * @return 值
     */
    public String lPop(String key) {
        return jedisCluster.lpop(key);
    }

    /**
     * 右侧弹出
     *
     * @param key 键
     * @return 值
     */
    public String rPop(String key) {
        return jedisCluster.rpop(key);
    }

    // ============================== 分布式锁 ==============================

    private static final String LOCK_PREFIX = "lock:";
    private static final String LOCK_VALUE_PREFIX = "lock_";

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁的key
     * @param expire  锁过期时间（秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, long expire) {
        String key = LOCK_PREFIX + lockKey;
        String value = LOCK_VALUE_PREFIX + Thread.currentThread().threadId();
        return BaseConstants.OK.equals(jedisCluster.set(key, value));
    }

    /**
     * 尝试获取分布式锁（带等待时间）
     *
     * @param lockKey  锁的key
     * @param expire   锁过期时间（秒）
     * @param waitTime 等待时间（毫秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, long expire, long waitTime) throws InterruptedException {
        String key = LOCK_PREFIX + lockKey;
        String value = LOCK_VALUE_PREFIX + Thread.currentThread().threadId();
        long startTime = System.currentTimeMillis();

        while (true) {
            if (BaseConstants.OK.equals(jedisCluster.set(key, value))) {
                return true;
            }
            if (System.currentTimeMillis() - startTime >= waitTime) {
                return false;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    /**
     * 释放分布式锁（使用Lua脚本保证原子性）
     *
     * @param lockKey 锁的key
     * @return 是否释放成功
     */
    public boolean unlock(String lockKey) {
        String key = LOCK_PREFIX + lockKey;
        String value = LOCK_VALUE_PREFIX + Thread.currentThread().threadId();

        // Lua脚本：只有锁的持有者才能释放锁
        String luaScript =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "    return redis.call('del', KEYS[1]) " +
                        "else " +
                        "    return 0 " +
                        "end";

        Object result = jedisCluster.eval(luaScript, 1, key, value);
        return Long.valueOf(1).equals(result);
    }

    /**
     * 判断是否持有锁
     *
     * @param lockKey 锁的key
     * @return 是否持有
     */
    public boolean isLocked(String lockKey) {
        String key = LOCK_PREFIX + lockKey;
        return jedisCluster.exists(key);
    }

    /**
     * 续期锁
     *
     * @param lockKey 锁的key
     * @param expire  新的过期时间（秒）
     * @return 是否续期成功
     */
    public boolean renewLock(String lockKey, long expire) {
        String key = LOCK_PREFIX + lockKey;
        String value = LOCK_VALUE_PREFIX + Thread.currentThread().threadId();

        // Lua脚本：只有锁的持有者才能续期
        String luaScript =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "    return redis.call('expire', KEYS[1], ARGV[2]) " +
                        "else " +
                        "    return 0 " +
                        "end";

        Object result = jedisCluster.eval(luaScript, 1, key, value, String.valueOf(expire));
        return Long.valueOf(1).equals(result);
    }
}
