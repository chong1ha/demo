package com.example.week1.test02.workflow;

import com.example.week1.common.job.AbstractTask;
import com.example.week1.dummy.database.job.DummyJobA;
import com.example.week1.dummy.database.job.DummyJobB;
import com.example.week1.dummy.database.model.DummyDomain;
import com.example.week1.dummy.database.service.DummyDomainServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * CronExp에 따른, 주기적으로 실행하는 스케줄러 클래스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/04 11:39 AM
 */
//@Component
public class JobScheduler02 {

    /** Cron 표현식 (스케줄러 시간용) */
    @Value("${jobs.cron-exp}")
    private String cronExp;

    /** 작업 리스트 */
    private final List<AbstractTask> tasks;
    /** 브로커서비스 서비스 구현체 */
    private final DummyDomainServiceImpl dummyDomainService;
    /** CronJob 목록 */
    private List<JobRunner02> jobRunners;
    /** 작업을 병렬로 실행할 ExecutorService */
    private ExecutorService executorService;

    @Autowired
    public JobScheduler02(List<AbstractTask> tasks, DummyDomainServiceImpl dummyDomainService, List<JobRunner02> jobRunners) {

        // 인스턴스 생성 (작업 추가, 추후 yaml)
        AbstractTask dummyJobA = new DummyJobA();
        AbstractTask dummyJobB = new DummyJobB();

        // 초기화
        this.tasks = List.of(dummyJobA, dummyJobB);
        this.dummyDomainService = dummyDomainService;
    }

    /**
     * Bean 초기화 이후, 호출
     */
    @PostConstruct
    private void init() {

        if (cronExp == null || cronExp.isEmpty()) {
            throw new IllegalStateException("CronExp02 is not set");
        }

        // 실행시키는 Task 갯수만큼 Pool 구성
        executorService = Executors.newFixedThreadPool(tasks.size());

        // JobRunner 리스트 초기화
        this.jobRunners = List.of(

                // CronExp에 따른, 스케줄 Job
                createJobRunner(cronExp, currentTime -> {
                    try {
                        run(currentTime);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

    /**
     * JobRunner 생성
     *
     * @param cronExp 크론 표현식
     * @param job 수행할 작업
     * @return JobRunner
     */
    private JobRunner02 createJobRunner(String cronExp, Consumer<Long> job) {

        try {
            return JobRunner02.builder()
                    .cronExp(cronExp)
                    .job(job)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating JobRunner", e);
        }
    }

    /**
     * 예약된 모든 작업 실행
     *
     * @throws Exception
     */
    public void startJobs() throws Exception {

        // JobRunner 실행
        for (JobRunner02 jobRunner : jobRunners) {
            try {
                jobRunner.run();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * 모든 실행 중인 작업 중지 및 ExecutorService 종료 <br>
     *
     * Bean 종료 시에 자동으로 호출
     *
     * @throws Exception
     */
    @PreDestroy
    private void stopJobs() throws Exception {

        // JobRunner 종료
        for (JobRunner02 jobRunner : jobRunners) {
            try {
                jobRunner.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ExecutorService 종료
        if (executorService != null) {
            executorService.shutdown();
            try {
                // 기존 작업 종료까지 대기
                if (executorService.awaitTermination(60, TimeUnit.SECONDS) == false) {
                    // 강제 종료
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                // 인터럽트 상태 유지 및 강제 종료
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 주기적으로 작업 실행
     */
    private void run(long currentTime) throws Exception {

        try {
            // DummyDomain 리스트 가져오기
            List<DummyDomain> domains = dummyDomainService.getActiveDummyDomains();
            if (domains == null || domains.isEmpty()) {
                return;
            }

            // 실행할 작업 저장
            List<Callable<Void>> tasklist = new ArrayList<>();

            // 각 도메인별
            for (DummyDomain domain : domains) {
                // 업무별 (수집에 따른)
                for (AbstractTask task : tasks) {
                    tasklist.add(() -> {
                        try {
                            // 초기화
                            task.init();

                            // 데이터 수집
                            List<Map<String, Object>> data = task.collect(currentTime, domain);

                            // 수집된 데이터 저장
                            if (data != null && !data.isEmpty()) {
                                task.save(data);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                task.exit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    });
                }
            }

            try {
                // 등록된 작업을 병렬로 실행
                executorService.invokeAll(tasklist);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
