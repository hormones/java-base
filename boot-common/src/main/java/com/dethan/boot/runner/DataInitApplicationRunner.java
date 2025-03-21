package com.dethan.boot.runner;

import com.dethan.boot.entity.User;
import com.dethan.boot.enums.UserState;
import com.dethan.boot.service.UserService;
import com.dethan.java.common.util.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitApplicationRunner implements ApplicationRunner {

    @Resource
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        // 生成测试数据
        User user = new User();
        user.setLoginName("dethan");
        user.setUserName(user.getLoginName());
        user.setEmail("dethan@example.com");
        user.setPhone("12345678901");
        user.setPassword("123456");
        user.setState(UserState.NORMAL);
        user = userService.save(user);
        log.info("init user: {}", JSONUtil.toJSONString(user));
    }
}
