package com.dethan.boot.web.controller;

import com.dethan.boot.annotation.SPELAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    @SPELAnnotation("@environment.getProperty('request.logging.enabled')")
    public String test(String name) {
        return name;
    }
}
