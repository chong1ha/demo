package com.example.week1.dummy.database.service;

import com.example.week1.dummy.database.mapper.DummyDomainMapper;
import com.example.week1.dummy.database.model.DummyDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
