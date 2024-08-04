package com.example.week1.dummy.database.service;

import com.example.core.common.config.YamlPropertySourceFactory;
import com.example.week1.common.database.config.DataSourceConfig;
import com.example.week1.common.database.config.MyBatisConfig;
import com.example.week1.dummy.database.model.DummyDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Spring AOP의 프록시 메커니즘 <br>
 * '@Transactional(readOnly = true)'를 사용하면 Spring이 트랜잭션 처리를 위해 프록시를 생성하게 됨.
 * 즉, 이 프록시가 'DummyDomainServiceImpl'클래스를 감싸게 되며, 실제로 주입되는 객체는 프록시 객체가 됨.
 * 원본 클래스 대신 동작하기에, 테스트 시에 특정 타입의 빈을 기대할때, 프록시로 인해 타입이 달라지는 문제 발생.
 *
 * <p>
 * 해결법.
 *      'ServiceImpl 클래스 대신 Service Interface를 통해 주입받으면 프록시 객체 사용이 가능'
 *      'MockBean 사용.'
 * </p>
 *
 * @author kang-geonha
 * @since 2024/08/04 12:13 AM
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DataSourceConfig.class,
        MyBatisConfig.class,
        DummyDomainServiceImpl.class,
})
@TestPropertySource(value = "classpath:application-test.yml", factory = YamlPropertySourceFactory.class)
class DummyDomainServiceImplTest {

    /** 1. ServiceImpl 대신 Service interface 사용 */
    @Autowired
    private DummyDomainService dummyDomainService;

    /** 2. dummyDomainServiceImpl는 ApplicationContext에 의해 모의 객체로 교체됨 */
    @MockBean
    private DummyDomainServiceImpl dummyDomainServiceImpl;

    /**
     * 2. 모의 객체의 설정 (연습)
     */
    @BeforeEach
    void setup() throws Exception {

        List<DummyDomain> expectedDomainList = List.of(
                DummyDomain.builder()
                        .dummyId("D00001")
                        .dummyName("Name1")
                        .dummyDesc("Description1")
                        .dummyServiceYn('Y')  // 활성화
                        .build(),
                DummyDomain.builder()
                        .dummyId("D00003")
                        .dummyName("Name3")
                        .dummyDesc("Description3")
                        .dummyServiceYn('Y')  // 활성화
                        .build()
        );

        // dummyDomainServiceImpl.getActiveDummyDomains()가 호출 시, expectedDomainList를 반환하도록 설정
        when(dummyDomainServiceImpl.getActiveDummyDomains()).thenReturn(expectedDomainList);
    }

    @Test
    @DisplayName("서비스가 활성화된 DummyDomain 객체 조회 - MockBean 사용")
    public void getActiveDummyDomainsTest1() throws Exception {
        // when
        List<DummyDomain> actualDomainList = dummyDomainServiceImpl.getActiveDummyDomains();

        // then
        assertNotNull(actualDomainList, "결과는 null이 아니어야 함");
        assertEquals(2, actualDomainList.size(), "크기는 동일");

        assertEquals("D00001", actualDomainList.get(0).getDummyId(), "Dummy ID 일치");
        assertEquals("Name1", actualDomainList.get(0).getDummyName(), "Dummy Name 일치");
        assertEquals("Description1", actualDomainList.get(0).getDummyDesc(), "Dummy Desc 일치");
        assertEquals('Y', actualDomainList.get(0).getDummyServiceYn(), "Dummy Service YN 일치");
    }

    @Test
    @DisplayName("서비스가 활성화된 DummyDomain 객체 조회 - 서비스 인터페이스 사용")
    public void getActiveDummyDomainsTest2() throws Exception {

        try {
            //given
            List<DummyDomain> expectedDomainList = List.of(
                    DummyDomain.builder()
                            .dummyId("D00001")
                            .dummyName("Name1")
                            .dummyDesc("Description1")
                            .dummyServiceYn('Y')  // 활성화
                            .build(),
                    DummyDomain.builder()
                            .dummyId("D00003")
                            .dummyName("Name3")
                            .dummyDesc("Description3")
                            .dummyServiceYn('Y')  // 활성화
                            .build()
            );

            //when
            List<DummyDomain> actualDomainList = dummyDomainService.getActiveDummyDomains();

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