package com.example.week1.dummy.domain.service;

import com.example.week1.dummy.domain.model.DummyDomain;

import java.util.List;
import java.util.Map;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-07-16 오후 3:21
 */
public interface DummyTask {

    void init() throws Exception;
    void exit() throws Exception;

    List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception;
    void save(List<Map<String, Object>> data) throws Exception;
}
