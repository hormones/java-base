package com.dethan.java.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RandomUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CHINESE_CHARACTERS = generateChineseCharacters();
    private static final Random RANDOM = new Random();

    /**
     * 生成指定范围内的数字
     */
    public static int generate(int from, int to) {
        return RANDOM.nextInt(to - from) + from;
    }

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


    /**
     * 生成固定大小的随机文件
     *
     * @param name 文件名
     * @param size 文件大小，单位：字节
     * @throws IOException 如果文件写入失败
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void generateRandomTxtFile(String name, long size) throws IOException {
        if (!name.endsWith(".txt") || !name.endsWith(".text")) {
            name += ".txt";
        }
        File file = new File(name);
        if (file.exists()) {
            file.delete();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            long writtenBytes = 0;
            while (writtenBytes < size) {
                String randomContent = generateRandomContent(1024); // 每次写入1024个字符
                writer.write(randomContent);
                writtenBytes += randomContent.getBytes(StandardCharsets.UTF_8).length;
            }
        }
    }

    /**
     * 生成随机内容
     *
     * @param length 内容长度
     * @return 随机内容字符串
     */
    @SuppressWarnings("SameParameterValue")
    private static String generateRandomContent(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (RANDOM.nextBoolean()) {
                // 随机选择中文字符或英文字符
                if (RANDOM.nextBoolean()) {
                    // 生成中文字符
                    int randomIndex = RANDOM.nextInt(CHINESE_CHARACTERS.length());
                    sb.append(CHINESE_CHARACTERS.charAt(randomIndex));
                } else {
                    // 生成英文字母或数字
                    int randomIndex = RANDOM.nextInt(CHARACTERS.length());
                    sb.append(CHARACTERS.charAt(randomIndex));
                }
            } else {
                // 生成空格
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 生成一定范围的中文字符
     *
     * @return 中文字符字符串
     */
    private static String generateChineseCharacters() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0x4E00; i <= 0x9FA5; i++) {
            sb.append((char) i);
        }
        return sb.toString();
    }
}
