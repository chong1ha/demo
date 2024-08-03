package com.example.week1.common.job;

import com.example.week1.dummy.database.model.DummyDomain;

import java.util.List;
import java.util.Map;

/**
 * {@link Task} 의 추상 클래스
 *
 * <p>
 *     필요없는 메소드 구현 계속하지 않기위해.
 *     공통 로직 추출.
 * </p>
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-02 오후 1:03
 */
public class AbstractTask implements Task {

    /**
     * 초기화 수행
     */
    @Override
    public void init() throws Exception {}

    /**
     * 종료시 호출
     */
    @Override
    public void exit() throws Exception {}

    /**
     * 데이터 수집
     *
     * @param domain DummyDomain
     * @param time 수집 시간
     * @throws Exception 수집시 발생한 예외
     * @return 수집된 데이터 리스트
     */
    @Override
    public List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception {
        return List.of();
    }

    /**
     * 수집된 데이터 저장
     *
     * @param data 저장할 데이터 리스트
     * @throws Exception 저장시 발생한 예외
     */
    @Override
    public void save(List<Map<String, Object>> data) throws Exception {}
}
