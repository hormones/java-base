package com.dethan.java.common.define;

import com.dethan.java.common.enums.IBaseEnum;

public interface KeyInterface<T extends IBaseEnum> {

    T getKey();
}
