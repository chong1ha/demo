package com.example.week1.dummy.database.mapper;

import com.example.core.common.config.YamlPropertySourceFactory;
import com.example.week1.common.database.config.DataSourceConfig;
import com.example.week1.common.database.config.MyBatisConfig;
import com.example.week1.dummy.database.model.DummyDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author kang-geonha
 * @since 2024/08/03 7:43 PM
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DataSourceConfig.class,
        MyBatisConfig.class
})
@TestPropertySource(value = "classpath:application-test.yml", factory = YamlPropertySourceFactory.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
class DummyDomainMapperTest {

    @Autowired
    private DummyDomainMapper dummyDomainMapper;

    @BeforeEach
    public void setup() {
        // insert
    }


    @Test
    @DisplayName("selectAll(),  Mybatis DB 테스트")
    public void selectAllTest() throws Exception {
        try {
            //given
            List<DummyDomain> expectedDomainList = List.of(
                    DummyDomain.builder()
                            .dummyId("D00001")
                            .dummyName("Name1")
                            .dummyDesc("Description1")
                            .dummyServiceYn('Y')
                            .build(),
                    DummyDomain.builder()
                            .dummyId("D00002")
                            .dummyName("Name2")
                            .dummyDesc("Description2")
                            .dummyServiceYn('N')
                            .build(),
                    DummyDomain.builder()
                            .dummyId("D00003")
                            .dummyName("Name3")
                            .dummyDesc("Description3")
                            .dummyServiceYn('Y')
                            .build()
            );

            //when
            List<DummyDomain> actualDomainList = dummyDomainMapper.selectAll();

            //then
            assertNotNull(actualDomainList, "결과는 null이 아니어야 함");
            assertEquals(expectedDomainList.size(), actualDomainList.size(), "크기는 동일");

            for (int i = 0; i < expectedDomainList.size(); i++) {
                DummyDomain expected = expectedDomainList.get(i);
                DummyDomain actual = actualDomainList.get(i);

                assertEquals(expected.getDummyId(), actual.getDummyId(), "Dummy ID 일치");
                assertEquals(expected.getDummyName(), actual.getDummyName(), "Dummy Name 일치");
                assertEquals(expected.getDummyDesc(), actual.getDummyDesc(), "Dummy Desc 일치");
                assertEquals(expected.getDummyServiceYn(), actual.getDummyServiceYn(), "Dummy Service YN 일치");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}