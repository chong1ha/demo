package com.example.week1;

import com.example.week1.test01.workflow.JobRunner01;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * @author kang-geonha
 * @since 2024/08/04 10:45 AM
 */
@SpringBootTest
class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * ApplicationContext에 Bean으로 등록되나,
     * 실제 구현체를 모의객체로 대체
     */
    @MockBean
    private JobRunner01 jobRunner01;

    @Test
    @DisplayName("ApplicationContext 로드되는지")
    public void contextLoadsTest() throws Exception {
        try {
            //given
            //when
            //then
            assertThat(applicationContext).isNotNull();

            // JobRunner01 빈이 컨텍스트에 존재하는지 확인
            assertThat(applicationContext.containsBean("jobRunner01"))
                    .as("Bean 'jobRunner01' should exist in the application context")
                    .isTrue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("특정 Bean들이 ApplicationContext에 존재하는지")
    public void beanPresenceTest02() throws Exception {
        try {
            //given
            List<String> expectedBeanNames = List.of(
                    "sqlSessionFactory",
                    "appConfig",
                    "dataSourceConfig",
                    "myBatisConfig",
                    "dummyJobA",
                    "dummyDomainMapper"
            );

            //when
            String[] actualBeanNames = applicationContext.getBeanDefinitionNames();

            //then
            for (String expectedBeanName : expectedBeanNames) {
                // actualBeanNames 활용
                assertThat(Arrays.asList(actualBeanNames))
                        .as("Bean '%s' should exist in the application context", expectedBeanName)
                        .contains(expectedBeanName);
            }
            for (String expectedBeanName : expectedBeanNames) {
                // applicationContext.containBean 사용
                boolean beanExists = applicationContext.containsBean(expectedBeanName);
                assertThat(beanExists)
                        .as("Bean '%s' should exist in the application context", expectedBeanName)
                        .isTrue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}