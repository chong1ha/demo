package com.example.week1.test01.workflow.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 스케줄링 작업을 수행하는 유틸리티 클래스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 1:03 AM
 */
@Component
public class TaskSchedulerUtil {

    private final ThreadPoolTaskScheduler taskScheduler;

    public TaskSchedulerUtil(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    /**
     * 고정 주기로 스케줄링된 작업을 등록
     *
     * @param task 실행할 작업
     * @param period 주기 (밀리초)
     * @param unit 시간 단위
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period, TimeUnit unit) {
        PeriodicTrigger trigger = new PeriodicTrigger(period, unit);
        return taskScheduler.schedule(task, trigger);
    }

    /**
     * 고정 주기로 스케줄링된 작업을 등록 <br>
     *
     * 작업 종료 후 지연
     *
     * @param task 실행할 작업
     * @param initialDelay 초기 지연 시간 (밀리초)
     * @param delay 지연 시간 (밀리초)
     * @param unit 시간 단위
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        return taskScheduler.schedule(task, triggerContext -> {
            long nextExecutionTime = triggerContext.lastScheduledExecutionTime().getTime() + unit.toMillis(delay);
            return new Date(nextExecutionTime).toInstant();
        });
    }

    /**
     * Cron 표현식을 사용하여 스케줄링된 작업을 등록
     *
     * @param task 실행할 작업
     * @param cronExpression Cron 표현식
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> scheduleWithCron(Runnable task, String cronExpression) {
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        return taskScheduler.schedule(task, cronTrigger);
    }

    /**
     * 종료
     */
    public void shutdown() {
        taskScheduler.shutdown();
    }
}
