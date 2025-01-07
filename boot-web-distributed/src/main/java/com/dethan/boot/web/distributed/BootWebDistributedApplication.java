package com.dethan.boot.web.distributed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dethan.boot.*")
@SpringBootApplication
public class BootWebDistributedApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootWebDistributedApplication.class, args);
    }
}
