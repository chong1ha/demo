package com.example.week1.scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolTaskScheduler + ThreadPoolExecutor 설정 클래스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 12:59 AM
 */
@Configuration
public class SchedulerConfig {

    @Value("${scheduler.poolSize}")
    private int schedulerPoolSize;

    @Value("${scheduler.threadNamePrefix}")
    private String schedulerThreadNamePrefix;

    @Value("${executor.corePoolSize}")
    private int corePoolSize;

    @Value("${executor.maxPoolSize}")
    private int maximumPoolSize;

    @Value("${executor.queueCapacity}")
    private int queueCapacity;

    @Value("${executor.threadNamePrefix}")
    private String executorThreadNamePrefix;


    /**
     * 주기적 실행 작업 처리용, 스케줄러 설정
     *
     * @return 설정된 {@link ThreadPoolTaskScheduler}
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(schedulerPoolSize);
        scheduler.setThreadNamePrefix(schedulerThreadNamePrefix);
        scheduler.initialize();
        return scheduler;
    }

    /**
     * 멀티스레드 작업 처리용, 쓰레드 풀 설정
     *
     * @return 설정된 {@link ThreadPoolExecutor}
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                corePoolSize,         // 코어 풀 크기
                maximumPoolSize,      // 최대 풀 크기
                60L,                  // 쓰레드 유지 시간
                TimeUnit.SECONDS,     // 시간 단위
                new LinkedBlockingQueue<>(queueCapacity),          // 큐 용량
                new CustomThreadFactory(executorThreadNamePrefix)  // 쓰레드 팩토리
        );
    }

    /**
     * 사용자 정의, 쓰레드 팩토리 클래스
     */
    private static class CustomThreadFactory implements java.util.concurrent.ThreadFactory {
        private final String threadNamePrefix;
        private int count = 0;

        CustomThreadFactory(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, threadNamePrefix + count++);
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }
}
