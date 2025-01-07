package com.dethan.boot.web.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RequestUtil {

    private final static Map<Class<?>, Boolean> CACHE = new ConcurrentHashMap<>();

    public static boolean isRestRequest(HttpServletRequest request) {
        Object attribute = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (Objects.isNull(attribute) || !(attribute instanceof HandlerMethod handlerMethod)) {
            return false;
        }
        return isRestController(handlerMethod.getBeanType());
    }


    private static boolean isRestController(Class<?> beanType) {
        return CACHE.computeIfAbsent(beanType, clazz -> clazz.isAnnotationPresent(RestController.class));
    }
}
