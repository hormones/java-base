package com.dethan.boot.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Lazy(false)
public class AppContext implements ApplicationContextAware {

    private static final Map<String, Object> BEAN_NAME_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Object> BEAN_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Map<String, ?>> BEANS_CACHE = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;

    /**
     * 取得存储在静态变量中的ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        AppContext.applicationContext = applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        if (BEAN_NAME_CACHE.containsKey(name)) {
            return (T) BEAN_NAME_CACHE.get(name);
        }
        T bean = (T) applicationContext.getBean(name);
        BEAN_NAME_CACHE.put(name, bean);
        return bean;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     * 如果有多个Bean符合Class, 取出第一个
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> type) {
        checkApplicationContext();
        if (BEAN_CACHE.containsKey(type)) {
            return (T) BEAN_CACHE.get(type);
        }
        T bean = applicationContext.getBean(type);
        BEAN_CACHE.put(type, bean);
        return bean;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     * 如果有多个Bean符合Class, 取出第一个
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getBeans(Class<T> type) {
        checkApplicationContext();
        if (BEANS_CACHE.containsKey(type)) {
            return (Map<String, T>) BEANS_CACHE.get(type);
        }
        Map<String, T> beans = applicationContext.getBeansOfType(type);
        BEANS_CACHE.put(type, beans);
        return beans;
    }

    public static void publishEvent(ApplicationEvent event) {
        checkApplicationContext();
        applicationContext.publishEvent(event);
    }

    /**
     * 检查是否存在
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext未注入");
        }
    }
}
