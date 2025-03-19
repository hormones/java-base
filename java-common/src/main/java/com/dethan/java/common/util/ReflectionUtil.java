package com.dethan.java.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

    public static String methodToProperty(String name) throws NoSuchFieldException {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new NoSuchFieldException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            name = name.substring(3);
        }

        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }
}
