package com.dethan.boot.distributed.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class SessionConfig {

    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}