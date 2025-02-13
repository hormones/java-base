package com.dethan.java.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomUtil {

    /**
     * 生成任意长度的字符串
     */
    public static String generateWords(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be non-negative");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) (Math.random() * 26 + 'a'));
        }
        return sb.toString();
    }
}
