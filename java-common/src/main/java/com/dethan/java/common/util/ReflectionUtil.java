package com.dethan.java.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectionUtil {

    /**
     * 获取当前类以及父类所有的属性
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        List<Field> fields = new ArrayList<>();
        // 获取字段
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            Collections.addAll(fields, declaredFields);
            // 父类
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
