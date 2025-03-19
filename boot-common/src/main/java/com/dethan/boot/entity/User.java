package com.dethan.boot.entity;

import com.dethan.boot.enums.UserState;
import com.dethan.java.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "users")
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(unique = true, length = 100, nullable = false)
    private String loginName;

    @Column(unique = true, length = 100, nullable = false)
    private String userName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(unique = true, length = 100, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserState state;
}
