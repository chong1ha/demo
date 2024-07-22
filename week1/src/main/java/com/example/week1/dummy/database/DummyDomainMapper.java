package com.example.week1.dummy.database;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오전 10:13
 */
@Mapper
@Repository
public interface DummyDomainMapper {

    List<Map<String, Object>> selectAll() throws Exception;
}
