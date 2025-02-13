package com.dethan.boot.web.security.entity;

import com.dethan.java.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Data
@Table(name = "users")
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements ApplicationRunner {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 生成测试数据
        User user = new User();
        user.setFullName("Dethan");
        user.setEmail("dethan@example.com");
        user.setPassword("123456");
        // user.save();
    }
}
