package com.dethan.java.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserState implements KeyEnum {

    NORMAL(1, "正常"),

    LOCKED(2, "锁定"),

    DELETED(3, "删除");

    private final Integer key;

    private final String name;
}
