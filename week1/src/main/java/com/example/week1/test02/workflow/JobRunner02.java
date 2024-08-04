package com.example.week1.test02.workflow;

import com.example.week1.test02.workflow.util.CronExp02;
import lombok.Builder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * CronJob 수행 클래스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/04 11:38 AM
 */
public class JobRunner02 {

    /** 크론 시간 표현식 */
    private CronExp02 cronExp;
    /** 수행할 작업 (함수형 인터페이스, currentTime을 매개변수로 쓰는 용도) */
    private Consumer<Long> job;
    /** 스케줄링 관련 */
    private final ScheduledExecutorService scheduler;
    /** 예약된 작업 */
    private ScheduledFuture<?> scheduledFuture;

    /**
     * 생성자
     *
     * @param cronExp 크론 시간 표현식
     * @param job 수행할 작업
     */
    @Builder
    public JobRunner02(String cronExp, Consumer<Long> job) throws Exception {
        this.setCronExp(cronExp);
        this.setJob(job);
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * 크론 시간 표현식 설정
     *
     * @param cronExp 크론 시간 표현식 문자열
     * @throws Exception 크론 표현식 생성 중 오류 발생 시
     */
    public void setCronExp(String cronExp) throws Exception {

        if (cronExp == null || cronExp.trim().isEmpty()) {
            throw new IllegalArgumentException("CronExp must not be null or empty");
        }

        this.cronExp = CronExp02.create(cronExp);
    }

    /**
     * 설정된 크론 시간 표현식 반환
     *
     * @return 설정된 시간 표현식
     */
    public String getCronExp() {
        return this.cronExp.getCronExp();
    }

    /**
     * 수행할 작업 설정
     *
     * @param job 수행할 작업
     * @throws NullPointerException job이 null일 경우
     */
    public void setJob(Consumer<Long> job) {
        if (job == null) {
            throw new NullPointerException("Job is null");
        }
        this.job = job;
    }

    /**
     * cron job 수행 <br>
     *
     * 현재 시간 기준, 다음 실행 시간을 계산하여 작업을 예약
     */
    public void run() {
        // 현재 시간
        long currentTime = System.currentTimeMillis();
        // 다음 실행 시간 계산
        long nextTime = cronExp.getNextTimeInMillis(currentTime);
        // 지연 시간 계산
        long delay = nextTime - System.currentTimeMillis();

        if (delay < 0) {
            delay = 0;
        }

        // 작업 예약
        scheduledFuture = scheduler.schedule(() -> {
            job.accept(nextTime);  // 작업 실행
            run();  // 다음 실행 시간 예약
        }, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 현재 작업 중지 및 예약된 작업 취소
     */
    public void stop() {
        // 예약된 작업 취소
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }

        // 스케줄러 종료
        scheduler.shutdown();
        try {
            // 정상 종료시까지 기다림
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                // 강제 종료
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
