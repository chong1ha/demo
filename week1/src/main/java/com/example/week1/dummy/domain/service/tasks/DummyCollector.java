package com.example.week1.dummy.domain.service.tasks;

import com.example.week1.dummy.database.DummyDomainService;
import com.example.week1.dummy.domain.model.DummyDomain;
import com.example.week1.dummy.domain.service.DummyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/21 11:50 PM
 */
@Component
public class DummyCollector implements DummyTask {

    private final DummyDomainService dummyDomainService;

    @Autowired
    public DummyCollector(DummyDomainService dummyDomainService) {
        this.dummyDomainService = dummyDomainService;
    }

    @Override
    public void init() throws Exception {
        System.out.println("Task initialized");
    }

    @Override
    public void exit() throws Exception {
        System.out.println("Task exited");
    }

    @Override
    public List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception {

        System.out.println("Data collected at: " + time);

        List<Map<String, Object>> data = dummyDomainService.getAllDummyDomains();
        return (data != null) ? data : Collections.emptyList();
    }

    @Override
    public void save(List<Map<String, Object>> data) throws Exception {
        System.out.println("Data saved: " + data);
    }
}
