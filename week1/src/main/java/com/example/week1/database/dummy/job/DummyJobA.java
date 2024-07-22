package com.example.week1.database.dummy.job;

import com.example.week1.database.dummy.model.DummyDomain;

import java.util.List;
import java.util.Map;

/**
 * DummyJob 1
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 12:15 AM
 */
public class DummyJobA implements DummyTask {

    @Override
    public void init() throws Exception {
        System.out.println("DummyJob 1: initialized");
    }

    @Override
    public void exit() throws Exception {
        System.out.println("DummyJob 1: exited");
    }

    @Override
    public List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception {
        System.out.println("DummyJob 1: Data collected at: " + time);
        return List.of();
    }

    @Override
    public void save(List<Map<String, Object>> data) throws Exception {
        System.out.println("DummyJob 1: Data saved: " + data);
    }
}
