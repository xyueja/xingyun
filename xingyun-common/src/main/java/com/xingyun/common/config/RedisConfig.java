/*
 * Copyright (c) 2026 XingYun. All rights reserved.
 */

package com.xingyun.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Redis配置类
 *
 * @author yxuej
 * @since 2026-03-28
 */
@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${spring.data.redis.cluster.nodes:127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381}")
    private String clusterNodes;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.cluster.max-redirects:3}")
    private int maxRedirects;

    /**
     * Redis集群配置
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        List<String> nodes = Arrays.asList(clusterNodes.split(","));
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(nodes);
        clusterConfig.setMaxRedirects(maxRedirects);
        if (password != null && !password.isEmpty()) {
            clusterConfig.setPassword(password);
        }
        return clusterConfig;
    }

    /**
     * Jedis连接工厂
     * 内部已创建JedisCluster，可通过getNativeConnection()获取
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(5))
                .usePooling()
                .poolConfig(jedisPoolConfig())
                .build();
        return new JedisConnectionFactory(redisClusterConfiguration(), clientConfig);
    }

    /**
     * Jedis连接池配置
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(100);
        // 最大空闲连接数
        poolConfig.setMaxIdle(50);
        // 最小空闲连接数
        poolConfig.setMinIdle(10);
        // 获取连接时的最大等待毫秒数
        poolConfig.setMaxWait(Duration.ofSeconds(3));
        // 在获取连接时检查有效性
        poolConfig.setTestOnBorrow(true);
        // 在归还连接时检查有效性
        poolConfig.setTestOnReturn(false);
        // 空闲时检查有效性
        poolConfig.setTestWhileIdle(true);
        // 空闲连接检测周期
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(30));
        // 连接最小空闲时间，超过此值则被驱逐
        poolConfig.setMinEvictableIdleDuration(Duration.ofMinutes(1));
        return poolConfig;
    }

    /**
     * RedisTemplate（Spring Data Redis）
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        // 使用String序列化器作为key的序列化器
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // 使用JSON序列化器作为value的序列化器
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * JedisCluster（Jedis原生客户端）
     * 从JedisConnectionFactory获取，避免重复创建
     */
    @Bean
    public JedisCluster jedisCluster() {
        return (JedisCluster) jedisConnectionFactory().getClusterConnection().getNativeConnection();
    }
}
