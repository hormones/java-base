package com.dethan.java.common.define;

import com.dethan.java.common.enums.KeyEnum;

public interface KeyInterface<T extends KeyEnum> {

    T getKey();
}
