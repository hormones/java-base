package com.dethan.java.common.define;

import com.dethan.java.common.enums.IBaseEnum;

public interface KeyInterface<T extends IBaseEnum> {
    KeyInterface<?> MISSED = new MissedKeyInterface();

    T getKey();

    @SuppressWarnings("rawtypes")
    final class MissedKeyInterface implements KeyInterface {
        @Override
        public IBaseEnum getKey() {
            return null;
        }
    }
}
