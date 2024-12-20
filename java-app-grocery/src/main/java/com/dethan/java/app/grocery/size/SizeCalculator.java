package com.dethan.java.app.grocery.size;

import org.apache.lucene.util.RamUsageEstimator;

import java.util.HashMap;
import java.util.Map;

public class SizeCalculator {

    public static void main(String[] args) {
        Map<String, Object> obj = new HashMap<>();
        // 先放入1000个键值对
        for (int i = 0; i < 1000; i++) {
            obj.put("key" + i, "value" + i);
        }
        // 获取对象的实际大小
        System.out.println("====================================");
        System.out.println("obj size: " + RamUsageEstimator.sizeOfMap(obj) + " bytes");

        System.out.println("====================================");
        // 再放入1000个键值对
        for (int i = 1000; i < 2000; i++) {
            obj.put("key" + i, "value" + i);
        }
        System.out.println("obj size: " + RamUsageEstimator.sizeOfMap(obj) + " bytes");
        System.out.println("====================================");

        // 查看对象内存分布
        // String layout = ClassLayout.parseInstance(obj).toPrintable();
        // System.out.println("obj layout: " + layout);
        // System.out.println("====================================");
    }
}
