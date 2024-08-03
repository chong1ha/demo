package com.example.week1.common.database.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MyBatis 설정을 외부 설정 파일에서 읽어오는 프로퍼티 클래스
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오전 10:35
 */
@ConfigurationProperties(prefix = "mybatis")
@Getter @Setter
public class MyBatisProperties {

    /** MyBatis Mapper XML 파일의 경로 */
    private String mapperLocations;
    /** MyBatis 타입 별칭 패키지 경로 */
    private String typeAliasesPackage;
}
