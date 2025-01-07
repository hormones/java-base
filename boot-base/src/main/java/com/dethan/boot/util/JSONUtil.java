package com.dethan.boot.util;

import com.dethan.boot.enums.IBaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JSONUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final JavaType TYPE_MAP_STRING;
    public static final JavaType TYPE_MAP;

    static {
        SimpleModule module = new SimpleModule();
        // IBaseEnum 枚举字段添加翻译字段
        module.addSerializer(IBaseEnum.class, new IBaseEnum.IBaseEnumJacksonSerializer());
        // LocalDateTime 序列化配置
        module.addSerializer(LocalDateTime.class, new LocalDateTimestampSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimestampDeserializer());
        OBJECT_MAPPER.registerModule(module);
        // OBJECT_MAPPER.registerModule(new JavaTimeModule());

        // 反序列化时忽略对象中不存在的json字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TYPE_MAP = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, Object.class);

        TYPE_MAP_STRING = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, String.class);
    }

    public static String toJSONString(Object object) {
        if (Objects.isNull(object)) {
            return "null";
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> json2Map(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, TYPE_MAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> json2MapString(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, TYPE_MAP_STRING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> object2Map(Object obj) {
        if (Objects.isNull(obj)) {
            return new HashMap<>();
        }
        try {
            return OBJECT_MAPPER.convertValue(obj, Map.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return new HashMap<>();
    }

    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.convertValue(map, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * jackson反序列化时间戳为LocalDateTime
     *
     * @since 0.0.1
     */
    public static class LocalDateTimestampDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
            long timestamp = parser.getValueAsLong();
            return timestamp < 0 ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        }
    }

    /**
     * jackson序列化LocalDateTime为时间戳
     *
     * @since 0.0.1
     */
    public static class LocalDateTimestampSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                long timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
                // gen.writeString(String.valueOf(timestamp));
            }
        }
    }
}
