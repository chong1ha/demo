package com.example.week1.dummy.application.scheduler;

import com.example.week1.dummy.domain.model.DummyData;
import com.example.week1.dummy.domain.model.DummyDomain;
import com.example.week1.dummy.domain.service.DummyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/21 11:40 PM
 */
@Component
public class DummyTaskScheduler {

    private final DummyTask task;

    @Autowired
    public DummyTaskScheduler(DummyTask task) {
        this.task = task;
    }

    @Scheduled(fixedRate = 10000)
    public void run() {

        System.out.println("Scheduled task executed");

        try {
            task.init();

            DummyDomain domain = DummyData.getDummyDomain();
            List<Map<String, Object>> data = task.collect(System.currentTimeMillis(), domain);
            task.save(data);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            try {
                task.exit();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }
}
