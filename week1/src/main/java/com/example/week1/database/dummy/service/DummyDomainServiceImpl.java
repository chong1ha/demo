package com.example.week1.database.dummy.service;

import com.example.week1.database.dummy.mapper.DummyDomainMapper;
import com.example.week1.database.dummy.model.DummyDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @Override
    @Transactional(readOnly = true)
    public List<DummyDomain> getAllDomains() throws Exception {
        try {
            return dummyDomainMapper.selectAll();
        } catch (Exception e) {
            throw new Exception("Error fetching all domains", e);
        }
    }
}
