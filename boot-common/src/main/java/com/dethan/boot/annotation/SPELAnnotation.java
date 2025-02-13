package com.dethan.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // 适用于方法
@Retention(RetentionPolicy.RUNTIME)  // 保留到运行时
public @interface SPELAnnotation {

    String value();  // 定义一个 value 参数，用于传入 SpEL 表达式
}
