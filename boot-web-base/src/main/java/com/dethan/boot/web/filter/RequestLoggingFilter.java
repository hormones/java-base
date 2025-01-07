package com.dethan.boot.web.filter;

import com.dethan.boot.web.util.RequestUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ConditionalOnProperty(name = "request.logging.enabled", havingValue = "true")
public class RequestLoggingFilter implements Filter {
    private final Map<Class<?>, Boolean> CACHE = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (RequestUtil.isRestRequest(request)) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            log.info("[{}][{}] {} {} ms", request.getMethod(), response.getStatus(),
                    request.getRequestURI(), System.currentTimeMillis() - startTime);
        }
    }
}
