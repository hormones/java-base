package com.dethan.boot.config;

import com.dethan.boot.util.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BaseConfig {

    /**
     * 自定义的ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JSONUtil.getObjectMapper();
    }
}
