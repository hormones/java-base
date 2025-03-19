package com.dethan.boot.util;

import com.dethan.java.common.define.KeyInterface;
import com.dethan.java.common.enums.IBaseEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Lazy(false)
public class AppContext implements ApplicationContextAware {

    private static final Map<String, Object> BEAN_NAME_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Object> BEAN_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Map<String, ?>> BEANS_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<KeyInterface<?>>, Map<IBaseEnum, KeyInterface<?>>> BEAN_KEY_CACHE = new ConcurrentHashMap<>();

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
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> type) {
        checkApplicationContext();
        return (T) BEAN_CACHE.computeIfAbsent(type, k -> applicationContext.getBean(type));
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     * 如果有多个Bean匹配Class+Enum, 则抛出异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<KeyInterface<?>> type, IBaseEnum enumValue) {
        checkApplicationContext();
        Map<IBaseEnum, KeyInterface<?>> caches = BEAN_KEY_CACHE.computeIfAbsent(type, k -> new ConcurrentHashMap<>());
        KeyInterface<?> bean = caches.computeIfAbsent(enumValue, k -> {
            Map<String, KeyInterface<?>> beans = getBeans(type);
            List<KeyInterface<?>> findBeans = beans.values().stream()
                    .filter(b -> Objects.equals(b.getKey(), enumValue))
                    .toList();
            if (findBeans.isEmpty()) {
                return KeyInterface.MISSED;
            }
            if (findBeans.size() == 1) {
                return findBeans.getFirst();
            }
            String beanNames = findBeans.stream().map(b -> b.getClass().getName()).collect(Collectors.joining("\n"));
            throw new RuntimeException("find multiple beans for interface %s with type %s: \n%s"
                    .formatted(type.getName(), enumValue.getName(), beanNames));
        });
        if (bean == KeyInterface.MISSED) {
            return null;
        }
        return (T) bean;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getBeans(Class<T> type) {
        checkApplicationContext();
        return (Map<String, T>) BEANS_CACHE.computeIfAbsent(type, k -> applicationContext.getBeansOfType(type));
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
