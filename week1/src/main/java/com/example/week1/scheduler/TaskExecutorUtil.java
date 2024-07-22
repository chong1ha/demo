package com.example.week1.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 멀티쓰레딩 작업 처리를 위한 유틸리티 클래스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 1:12 AM
 */
@Component
public class TaskExecutorUtil {

    private final ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    public TaskExecutorUtil(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public Future<?> submitTask(Runnable task) {
        return threadPoolExecutor.submit(task);
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
