package com.example.week1.database.dummy.service;

import com.example.week1.database.dummy.model.DummyDomain;

import java.util.List;

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
     * @return DummyDomain 리스트
     */
    List<DummyDomain> getAllDomains() throws Exception;
}
