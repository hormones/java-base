package com.dethan.boot.web.security;

import com.dethan.boot.JavaBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dethan.*"})
public class BootWebSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBootApplication.class, args);
    }
}
