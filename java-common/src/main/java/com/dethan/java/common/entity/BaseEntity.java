package com.dethan.java.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础实体
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    protected Integer id;

    @Column(nullable = false, updatable = false)
    protected LocalDateTime createTime;

    @Column(nullable = false)
    protected LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        this.updateTime = this.createTime = LocalDateTime.now();  // 创建时间为当前时间，更新创建时间也是更新时间
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now(); // 设置更新时间为当前时间
    }
}
