package com.dethan.java.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * 枚举定义接口
 *
 * @since 0.0.1
 */
public interface KeyEnum {

    static <T extends KeyEnum> T fromKey(Class<T> enumType, Integer key) {
        if (Objects.isNull(key)) {
            return null;
        }
        T[] enums = enumType.getEnumConstants();
        for (T item : enums) {
            if (Objects.equals(item.getKey(), key)) {
                return item;
            }
        }
        return null;
    }

    /**
     * @return 枚举值
     * @since 0.0.1
     */
    @JsonValue
    Integer getKey();

    /**
     * @return 枚举显示名称
     * @since 0.0.1
     */
    String getName();

    /**
     * KeyEnum的jackson序列化方法，额外添加翻译字段
     *
     * @since 0.1
     */
    class KeyEnumJacksonSerializer extends JsonSerializer<KeyEnum> {

        private static final String TRANSLATE_LAST = "_trans";

        @Override
        public void serialize(KeyEnum anEnum, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (anEnum != null) {
                String fieldName = gen.getOutputContext().getCurrentName();
                gen.writeNumber(anEnum.getKey());
                gen.writeStringField(this.getTransName(fieldName), anEnum.getName());
            }
        }

        private String getTransName(String fieldName) {
            return fieldName + TRANSLATE_LAST;
        }
    }
}
