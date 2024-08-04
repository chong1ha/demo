package com.example.week1.common.job;

import com.example.week1.dummy.database.model.DummyDomain;
import com.example.week1.dummy.database.service.DummyDomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link Task} 의 추상 클래스
 *
 * <p>
 *     필요없는 메소드 구현 계속하지 않기위해.
 *     공통 로직 추출.
 * </p>
 * <p>
 *     이 클래스는 Lazy Loading을 이용하여
 *     필요할 때만 데이터를 로드하는 방식 사용.
 * </p>
 * <p>
 *     각 도메인별 쓰레드 내에서의 Task는
 *     같은 데이터맵을 공유하도록 설계.
 * </p>
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-02 오후 1:03
 */
@RequiredArgsConstructor
public class AbstractTask implements Task {

    private final DummyDomainService dummyDomainService;

    private Map<String, String> dataMap = new ConcurrentHashMap<>();
    private Character cachedServiceYn;


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
        System.out.println("Thread ID: " + Thread.currentThread().getId() +" Collecting data in task: " + this.getClass().getSimpleName() + " at time: " + time);
        return List.of();
    }

    /**
     * 수집된 데이터 저장
     *
     * @param data 저장할 데이터 리스트
     * @throws Exception 저장시 발생한 예외
     */
    @Override
    public void save(List<Map<String, Object>> data) throws Exception {
        System.out.println("Thread ID: " + Thread.currentThread().getId() +" Saving data in task: " + this.getClass().getSimpleName());
    }

    /**
     * dummyName에 해당하는 dummyId 반환 <br>
     *
     * Lazy Loading 방식으로 구현되어
     * 사용 시점에 데이터를 로드하여 반환합니다.
     *
     * @param serviceYn 서비스 여부
     * @param dummyName 조회할 dummyName
     * @return 해당 dummyName에 대한 dummyId
     * @throws Exception 데이터베이스 조회 중 오류 발생
     */
    protected String getDummyId(Character serviceYn, String dummyName) throws Exception {

        if (dataMap == null || !serviceYn.equals(cachedServiceYn)) {
            System.out.println("Thread ID: " + Thread.currentThread().getId() +" [getDummyId] Loading data map for serviceYn: " + serviceYn + " in task: " + this.getClass().getSimpleName());
            loadDummyCache(serviceYn);
        } else {
            System.out.println("Thread ID: " + Thread.currentThread().getId() +" [getDummyId] Using cached data map for serviceYn: " + serviceYn + " in task: " + this.getClass().getSimpleName());
        }
        return dataMap.get(dummyName);
    }

    /**
     * 데이터맵을 캐시에 로드
     *
     * @param serviceYn 서비스 여부
     * @throws Exception 데이터베이스 조회 중 오류 발생
     */
    private void loadDummyCache(Character serviceYn) throws Exception {
        dataMap = dummyDomainService.getDummyMap(serviceYn);
        cachedServiceYn = serviceYn;
        System.out.println("Thread ID: " + Thread.currentThread().getId() +" [loadDummyCache] Loaded data map: " + dataMap + " for serviceYn: " + serviceYn + " in task: " + this.getClass().getSimpleName());
    }
}
