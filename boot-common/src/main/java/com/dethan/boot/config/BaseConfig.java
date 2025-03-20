package com.dethan.boot.config;

import com.dethan.java.common.enums.KeyEnum;
import com.dethan.java.common.util.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Objects;

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

    /**
     * KeyEnum的webmvc参数转换器，使KeyEnum支持作为接口参数
     */
    class KeyEnumWebmvcConverter implements ConverterFactory<String, KeyEnum> {

        @Override
        public <T extends KeyEnum> Converter<String, T> getConverter(Class<T> targetType) {
            return new StringToEnum<>(targetType);
        }

        private record StringToEnum<T extends KeyEnum>(Class<T> targetType) implements Converter<String, T> {

            @Override
            public T convert(@Nullable String source) {
                if (Strings.isBlank(source)) {
                    return null;
                }
                int value;
                try {
                    value = Integer.parseInt(source);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to convert value to required type, " +
                            "value: " + source + ", type: " + targetType.getName(), e);
                }

                T[] enums = targetType.getEnumConstants();
                if (null == enums) {
                    return null;
                }
                for (T e : enums) {
                    if (Objects.equals(e.getKey(), value)) {
                        return e;
                    }
                }
                return null;
            }
        }
    }
}
