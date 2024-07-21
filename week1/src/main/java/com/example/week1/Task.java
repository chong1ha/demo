package com.example.week1;

import java.util.Map;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-07-16 오후 3:21
 */
public interface Task {

    void init();
    void exit();

    Map<String, Object> collect();
    void save(Map<String, Object> dataMap);
}
