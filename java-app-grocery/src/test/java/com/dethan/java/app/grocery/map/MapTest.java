package com.dethan.java.app.grocery.map;

import com.dethan.java.app.grocery.util.ThreadUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

    /**
     * hashMap模拟多线程put操作
     */
    @Test
    void hashMapMultiThreadPut() {
        int times = 100000;
        Map<String, Object> map = new ConcurrentHashMap<>();
        ThreadUtil.waitRunInForkJoinCommonPool(i -> map.put(i.toString(), i), times);
        Assertions.assertEquals(times, map.size(), "map size should be " + times);
    }
}
