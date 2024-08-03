package com.example.week1.dummy.database.job;

import com.example.core.common.util.CommonUtil;
import com.example.week1.common.job.AbstractTask;
import com.example.week1.dummy.database.model.DummyDomain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * DummyJob A
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 12:15 AM
 */
@Component
public class DummyJobA extends AbstractTask {

    @Override
    public void init() throws Exception {
        System.out.println("DummyJob A: init()");
    }

    @Override
    public List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception {
        System.out.println("DummyJob A: Data collected at: " + CommonUtil.longToOffsetDateTime(time));
        return List.of();
    }

    @Override
    public void save(List<Map<String, Object>> data) throws Exception {
        System.out.println("DummyJob A: Data saved: " + data);
    }
}
