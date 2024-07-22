package com.example.week1.dummy.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오후 1:24
 */
@Service
public class DummyDomainService {

    private final DummyDomainMapper dummyDomainMapper;

    @Autowired
    public DummyDomainService(DummyDomainMapper dummyDomainMapper) {
        this.dummyDomainMapper = dummyDomainMapper;
    }

    public List<Map<String, Object>> getAllDummyDomains() throws Exception {
        return dummyDomainMapper.selectAll();
    }
}
