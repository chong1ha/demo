package com.example.week1.dummy.database.service;

import com.example.week1.dummy.database.mapper.DummyDomainMapper;
import com.example.week1.dummy.database.model.DummyDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * DummyDomain에 대한 Service 구현체
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오후 1:24
 */
@Service
public class DummyDomainServiceImpl implements DummyDomainService {

    private static final Logger log = LoggerFactory.getLogger(DummyDomainServiceImpl.class);

    /** serviceYn에 따라 (dummyName-dummyId)를 캐시로 유지하는 맵 (캐싱 레이어 역할) */
    private final Map<Character, Map<String, String>> dummyCache = new ConcurrentHashMap<>();
    private final DummyDomainMapper dummyDomainMapper;


    @Autowired
    public DummyDomainServiceImpl(DummyDomainMapper dummyDomainMapper) {
        this.dummyDomainMapper = dummyDomainMapper;
    }


    /**
     * 모든 DummyDomain 객체 조회
     *
     * @throws Exception 가져오기 중 오류 발생
     * @return 모든 List<DummyDomain>
     */
    @Override
    @Transactional(readOnly = true)
    public List<DummyDomain> getAllDummyDomains() throws Exception {

        try {
            return dummyDomainMapper.selectAll();
        } catch (Exception e) {
            throw new Exception("Error fetching all dummyDomain", e);
        }
    }

    /**
     * 서비스가 활성화된 DummyDomain 객체 조회
     *
     * @throws Exception 가져오기 중 오류 발생
     * @return 수집이 활성화된 List<DummyDomain>
     */
    @Override
    @Transactional(readOnly = true)
    public List<DummyDomain> getActiveDummyDomains() throws Exception {

        try {
            // 모든 정보 가져오기
            List<DummyDomain> allBrokerServices = getAllDummyDomains();

            // 필터링, serviceYn이 활성화된 것만
            return allBrokerServices.stream()
                    .filter(dummy -> dummy.getDummyServiceYn() == 'Y')
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error fetching active dummyDomains", e);
        }
    }

    /**
     * 서비스 상태에 따른 DummyName-DummyId 매핑 반환
     *
     * @param serviceYn 서비스 여부
     * @throws Exception 데이터베이스 조회 중 오류 발생
     * @return dataMap(DummyName-DummyId)
     */
    @Override
    public Map<String, String> getDummyMap(Character serviceYn) throws Exception {

        // 캐시에서 먼저 조회
        if (dummyCache.containsKey(serviceYn)) {
            System.out.println("Thread ID: " + Thread.currentThread().getId() +" [getDummyMap] Returning cached map for serviceYn: " + serviceYn);
            return dummyCache.get(serviceYn);
        } else {
            // 캐시가 없으면 DB에서 조회
            System.out.println("Thread ID: " + Thread.currentThread().getId() + " [getDummyMap] Cache miss for serviceYn: " + serviceYn + ". Loading from DB...");
            Map<String, String> newDataMap = loadDummyFromDB(serviceYn);
            dummyCache.put(serviceYn, newDataMap);
            return newDataMap;
        }
    }

    /**
     * 데이터베이스에서 DummyName-DummyId 매핑을 로드
     *
     * @param serviceYn 서비스 여부
     * @return 로드된 DummyName-DummyId 맵
     * @throws Exception 데이터베이스 조회 중 오류 발생
     */
    private Map<String, String> loadDummyFromDB(Character serviceYn) throws Exception {

        List<Map<String, Object>> results = dummyDomainMapper.selectDummyMap(serviceYn);
        if (results.isEmpty()) {
            System.out.println("Thread ID: " + Thread.currentThread().getId() +" [loadDummyFromDB] No data found for serviceYn: " + serviceYn);
        }

        Map<String, String> dataMap = new HashMap<>();
        for (Map<String, Object> row : results) {
            String key = (String) row.get("key");
            String value = (String) row.get("value");
            dataMap.put(key, value);
        }

        System.out.println("Thread ID: " + Thread.currentThread().getId() + " [loadDummyFromDB] Loaded dataMap from DB for serviceYn: " + serviceYn + " is " + dataMap.toString());
        return dataMap;
    }
}
