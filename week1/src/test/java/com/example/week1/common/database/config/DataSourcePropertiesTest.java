package com.example.week1.common.database.config;

import com.example.core.common.YamlPropertySourceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kang-geonha
 * @since 2024/08/03 11:44 PM
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(value = "classpath:application-test.yml", factory = YamlPropertySourceFactory.class)
class DataSourcePropertiesTest {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    @Test
    @DisplayName("application-test.yml 의 데이터소스 프로퍼티 확인")
    public void ymlPropertiesTest() throws Exception {
        try {
            assertNotNull(driverClassName);
            assertNotNull(url);
            assertNotNull(username);
            assertNotNull(password);

            //given
            String expectedDriverClassName = "org.postgresql.Driver";
            String expectedUrl = "jdbc:postgresql://localhost:5432/demo";
            String expectedUsername = "postgres";
            String expectedPassword = "password";

            //when
            //then
            assertEquals(driverClassName, expectedDriverClassName);
            assertEquals(url, expectedUrl);
            assertEquals(username, expectedUsername);
            assertEquals(password, expectedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}