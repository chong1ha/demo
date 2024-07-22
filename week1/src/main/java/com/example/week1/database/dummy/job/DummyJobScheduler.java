package com.example.week1.database.dummy.job;

import com.example.week1.database.dummy.model.DummyDomain;
import com.example.week1.database.dummy.service.DummyDomainServiceImpl;
import com.example.week1.scheduler.TaskExecutorUtil;
import com.example.week1.scheduler.TaskSchedulerUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/21 11:40 PM
 */
@Component
public class DummyJobScheduler {

    /** 주기적으로 실행되는 작업 관리 */
    private final TaskSchedulerUtil taskSchedulerUtil;
    private final TaskExecutorUtil taskExecutorUtil;
    private final DummyDomainServiceImpl domainService;
    private final List<DummyTask> tasks;
    private final long fixedRate;
    @Autowired
    public DummyJobScheduler(TaskSchedulerUtil taskSchedulerUtil, TaskExecutorUtil taskExecutorUtil,
                             DummyDomainServiceImpl domainService, List<DummyTask> tasks,
                             @Value("${scheduler.fixedRate}") long fixedRate) {

        // 인스턴스 생성 (더미 용, 추후 yaml)
        DummyTask dummyJobA = new DummyJobA();
        DummyTask dummyJobB = new DummyJobB();

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
        taskSchedulerUtil.scheduleAtFixedRate(this::run, fixedRate, TimeUnit.SECONDS);
    }

    /**
     * 주기적으로 작업 실행
     */
    private void run() {

        long currentTime = System.currentTimeMillis();

        try {
            // DummyDomain 리스트 가져오기 (테스트용)
            List<DummyDomain> domains = domainService.getAllDomains();
            if (domains == null || domains.isEmpty()) {
                return;
            }

            // 각 브로커별
            for (DummyDomain domain : domains) {
                // 업무별 (수집에 따른)
                for (DummyTask task : tasks) {
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
