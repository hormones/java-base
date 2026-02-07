package com.dethan.java.common.util;

import org.junit.jupiter.api.Test;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RandomUtilTest {

    @Test
    void generateRandomTxtFile() throws IOException {
        long start = System.currentTimeMillis();
        long size = 1024 * 1024 * 1024; // 1GB
        RandomUtil.generateRandomTxtFile("1GB大小的测试文件", size);
        System.out.println("生成1GB大小的测试文件耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}