package com.example.week1.dummy.database.service;

import com.example.core.common.config.YamlPropertySourceFactory;
import com.example.week1.common.database.config.DataSourceConfig;
import com.example.week1.common.database.config.MyBatisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kang-geonha
 * @since 2024/08/04 5:51 PM
 */
@SpringBootTest
class DummyJobTest {

    @Autowired
    private DummyDomainService dummyDomainService;

    @Test
    @DisplayName("CacheMap 작동 확인 - 엄밀히는 Lazy Loading은 아님")
    public void getDummyDataTest() throws Exception {
        try {
            //given
            Character expectedServiceYn = 'Y';
            String expectedDummyName = "Name1";
            String expectedDummyId = "D00001";

//            //when
//            String actualDummyIdFirstCall = dummyDomainService.getDummyId(expectedServiceYn, expectedDummyName);
//            String actualDummyIdSecondCall = dummyDomainService.getDummyId(expectedServiceYn, expectedDummyName);
//
//            //then
//            assertEquals(expectedDummyId, actualDummyIdFirstCall);
//            assertEquals(expectedDummyId, actualDummyIdSecondCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}