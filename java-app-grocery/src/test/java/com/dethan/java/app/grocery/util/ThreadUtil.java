package com.dethan.java.app.grocery.util;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadUtil {
    private static final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    /**
     * 在 ForkJoinPool 中并行执行指定次数的任务。
     *
     * @param consumer 任务执行的 Consumer，该 Consumer 接受一个 Integer 参数
     * @param times    执行任务的次数，必须为非负
     */
    public static void waitRunInForkJoinCommonPool(Consumer<Integer> consumer, Integer times) {
        if (times < 0) {
            throw new IllegalArgumentException("times must be non-negative");
        }
        // 生成任务列表
        List<ForkJoinTask<?>> tasks = IntStream.range(0, times)
                .mapToObj(i -> forkJoinPool.submit(() -> consumer.accept(i)))
                .collect(Collectors.toList());

        // 等待所有任务完成
        tasks.forEach(ForkJoinTask::join);
    }
}
