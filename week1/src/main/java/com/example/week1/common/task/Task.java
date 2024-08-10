package com.example.week1.common.task;

import com.example.week1.dummy.database.model.DummyDomain;

import java.util.List;
import java.util.Map;

/**
 * Collect & Save 인터페이스
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-16 오후 3:21
 */
public interface Task {

    /**
     * 초기화 수행
     */
    void init() throws Exception;

    /**
     * 종료시 호출
     */
    void exit() throws Exception;

    /**
     * 데이터 수집
     *
     * @param domain 수집 대상 브로커 정보
     * @param time 수집 시간
     * @throws Exception 수집시 발생한 예외
     * @return 수집된 데이터 리스트
     */
    List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception;

    /**
     * 수집된 데이터 저장
     *
     * @param data 저장할 데이터 리스트
     * @throws Exception 저장시 발생한 예외
     */
    void save(List<Map<String, Object>> data) throws Exception;
}
