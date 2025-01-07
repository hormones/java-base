package com.dethan.boot.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.annotation.Nullable;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 枚举定义接口
 *
 * @since 0.0.1
 */
public interface IBaseEnum {

    static <T extends IBaseEnum> T fromValue(Class<T> enumType, Integer value) {
        if (Objects.isNull(value)) {
            return null;
        }
        T[] enums = enumType.getEnumConstants();
        for (T item : enums) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * @return 枚举显示名称
     * @since 0.0.1
     */
    String getName();

    /**
     * @return 枚举值
     * @since 0.0.1
     */
    @JsonValue
    Integer getValue();

    /**
     * IBaseEnum的jackson序列化方法，额外添加翻译字段
     *
     * @since 0.1
     */
    class IBaseEnumJacksonSerializer extends JsonSerializer<IBaseEnum> {

        private static final String TRANSLATE_LAST = "_trans";

        @Override
        public void serialize(IBaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                String fieldName = gen.getOutputContext().getCurrentName();
                gen.writeNumber(value.getValue());
                gen.writeStringField(this.getTransName(fieldName), value.getName());
            }
        }

        private String getTransName(String fieldName) {
            return fieldName + TRANSLATE_LAST;
        }
    }

    /**
     * IBaseEnum的webmvc参数转换器，使IBaseEnum支持作为接口参数
     */
    class IBaseEnumWebmvcConverter implements ConverterFactory<String, IBaseEnum> {

        @Override
        public <T extends IBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
            return new StringToEnum<>(targetType);
        }

        private record StringToEnum<T extends IBaseEnum>(Class<T> targetType) implements Converter<String, T> {

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
                    if (Objects.equals(e.getValue(), value)) {
                        return e;
                    }
                }
                return null;
            }
        }
    }
}
