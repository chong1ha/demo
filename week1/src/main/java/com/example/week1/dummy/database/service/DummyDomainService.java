package com.example.week1.dummy.database.service;

import com.example.core.common.exception.ServiceException;
import com.example.week1.dummy.database.model.DummyDomain;

import java.util.List;
import java.util.Map;

/**
 * DummyDomain에 대한 Service 인터페이스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 12:10 AM
 */
public interface DummyDomainService {

    /**
     * 모든 DummyDomain 객체를 조회
     *
     * @throws Exception 서비스 처리 중 발생한 예외
     * @return List<DummyDomain>
     */
    public List<DummyDomain> getAllDummyDomains() throws Exception;

    /**
     * 서비스가 활성화된 DummyDomain 객체 조회
     *
     * @throws Exception 서비스 처리 중 발생한 예외
     * @return 활성화된 List<DummyDomain>
     */
    List<DummyDomain> getActiveDummyDomains() throws Exception;


    Map<String, String> getDummyMap(Character serviceYn) throws Exception;
}
