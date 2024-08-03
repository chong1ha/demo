package com.example.week1.test01.workflow.config;

import com.example.week1.test01.workflow.util.TaskExecutorUtil;
import com.example.week1.test01.workflow.util.TaskSchedulerUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 설정 <br>
 *
 * 스케줄링과 멀티스레드 작업의 시작,종료 관리
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 1:24 AM
 */
@Component
public class AppConfig {

    private final TaskSchedulerUtil taskSchedulerUtil;
    private final TaskExecutorUtil taskExecutorUtil;

    @Autowired
    public AppConfig(TaskSchedulerUtil taskSchedulerUtil, TaskExecutorUtil taskExecutorUtil) {
        this.taskSchedulerUtil = taskSchedulerUtil;
        this.taskExecutorUtil = taskExecutorUtil;
    }

    @PostConstruct
    public void init() {
        registerTasks();
    }

    /**
     * 후크 등록
     */
    public void registerTasks() {
        // 애플리케이션 종료 시, 리소스 정리
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            try {
                taskSchedulerUtil.shutdown();
            } catch (Exception e) {
               e.printStackTrace();
            }

            try {
                taskExecutorUtil.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}
