package com.example.week1.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 스케줄링과 멀티스레드 작업의 시작과 종료를 관리
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 1:24 AM
 */
@Component
public class ApplicationManager {

    private final TaskSchedulerUtil taskSchedulerUtil;
    private final TaskExecutorUtil taskExecutorUtil;

    @Autowired
    public ApplicationManager(TaskSchedulerUtil taskSchedulerUtil, TaskExecutorUtil taskExecutorUtil) {
        this.taskSchedulerUtil = taskSchedulerUtil;
        this.taskExecutorUtil = taskExecutorUtil;
    }

    public void startTasks() {
        // 애플리케이션 종료 시 리소스 정리
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            taskSchedulerUtil.shutdown();
            taskExecutorUtil.shutdown();
        }));
    }
}
