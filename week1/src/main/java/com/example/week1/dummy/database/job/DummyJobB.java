package com.example.week1.dummy.database.job;

import com.example.core.common.util.CommonUtil;
import com.example.week1.common.job.Task;
import com.example.week1.dummy.database.model.DummyDomain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * DummyJob B
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 12:15 AM
 */
@Component
public class DummyJobB implements Task {

    @Override
    public void init() throws Exception {
        System.out.println("DummyJob B: init()");
    }

    @Override
    public void exit() throws Exception {
        System.out.println("DummyJob B: exit()");
    }

    @Override
    public List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception {
        System.out.println("DummyJob B: Data collected at: " + CommonUtil.longToOffsetDateTime(time));
        return List.of();
    }

    @Override
    public void save(List<Map<String, Object>> data) throws Exception {
        System.out.println("DummyJob B: Data saved: " + data);
    }
}
