package com.dethan.boot.distributed.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 分布式session配置，redis 序列化配置
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.session.redis", name = "namespace")
public class SessionConfig {

    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}