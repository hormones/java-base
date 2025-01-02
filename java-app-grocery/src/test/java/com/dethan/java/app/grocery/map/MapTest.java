package com.dethan.java.app.grocery.map;

import com.dethan.java.app.grocery.util.ThreadUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    /**
     * hashMap模拟多线程put操作
     */
    @Test
    void hashMapMultiThreadPut() {
        int times = 100000;
        Map<String, Object> map = new HashMap<>();
        ThreadUtil.waitRunInForkJoinCommonPool(i -> map.put(i.toString(), i), times);
        Assertions.assertEquals(times, map.size(), "map size should be " + times);
    }
}
