package com.example.week1.test01.workflow;

import com.example.week1.dummy.database.job.DummyJobA;
import com.example.week1.dummy.database.job.DummyJobB;
import com.example.week1.common.job.Task;
import com.example.week1.dummy.database.model.DummyDomain;
import com.example.week1.dummy.database.service.DummyDomainServiceImpl;
import com.example.week1.test01.workflow.util.TaskExecutorUtil;
import com.example.week1.test01.workflow.util.TaskSchedulerUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 주기적으로 작업을 실행하는 스케줄러 <br>
 * <br>
 * <p>
 *     스프링의 {@link TaskSchedulerUtil}을 사용, 설정된 주기(fixedRate)로 {@link #run()} 메서드를 반복적으로 실행.
 *     작업은 {@link TaskExecutorUtil}을 통해 병렬로 처리.
 *     각 작업은 멀티스레드 환경에서 비동기로 실행.
 * </p>
 * <p>
 *     {@code @Component} 어노테이션이 붙어 있는 상태에서 애플리케이션 실행 시, 스케줄링 작업이 자동으로 시작
 * </p>
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/21 11:40 PM
 */
//@Component
public class JobRunner01 {

    /** 주기적으로 실행되는 작업 관리 */
    private final TaskSchedulerUtil taskSchedulerUtil;
    private final TaskExecutorUtil taskExecutorUtil;
    private final DummyDomainServiceImpl domainService;
    private final List<Task> tasks;
    private final long fixedRate;
    @Autowired
    public JobRunner01(TaskSchedulerUtil taskSchedulerUtil, TaskExecutorUtil taskExecutorUtil,
                       DummyDomainServiceImpl domainService, List<Task> tasks,
                       @Value("${scheduler.fixedRate}") long fixedRate) {

        // 인스턴스 생성 (더미 용, 추후 yaml)
        Task dummyJobA = new DummyJobA();
        Task dummyJobB = new DummyJobB();

        this.tasks = List.of(dummyJobA, dummyJobB);
        this.domainService = domainService;
        this.taskSchedulerUtil = taskSchedulerUtil;
        this.taskExecutorUtil = taskExecutorUtil;
        this.fixedRate = fixedRate;
    }

    /**
     * 병렬 스케줄링
     */
    @PostConstruct
    public void init() {
        taskSchedulerUtil.scheduleAtFixedRate(this::run, fixedRate, TimeUnit.MILLISECONDS);
    }

    /**
     * 주기적으로 작업 실행
     */
    private void run() {

        long currentTime = System.currentTimeMillis();

        try {
            // DummyDomain 리스트 가져오기
            List<DummyDomain> domains = domainService.getActiveDummyDomains();
            if (domains == null || domains.isEmpty()) {
                return;
            }

            // 각 도메인별
            for (DummyDomain domain : domains) {
                // 업무별
                for (Task task : tasks) {
                    taskExecutorUtil.submitTask(() -> {
                        try {
                            // 초기화
                            task.init();

                            // 데이터 수집
                            List<Map<String, Object>> data = task.collect(currentTime, domain);
                            if (data != null && !data.isEmpty()) {
                                // 수집된 데이터 저장
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
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
