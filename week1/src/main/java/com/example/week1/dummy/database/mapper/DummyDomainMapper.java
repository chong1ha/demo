package com.example.week1.dummy.database.mapper;

import com.example.week1.dummy.database.model.DummyDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DummyDomain에 대한 Mapper 인터페이스
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오전 10:13
 */
@Mapper
@Repository
public interface DummyDomainMapper {

    /** 모든 DummyDomain 정보 조회 */
    List<DummyDomain> selectAll() throws Exception;
}
