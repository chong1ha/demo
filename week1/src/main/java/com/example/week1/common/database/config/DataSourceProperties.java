package com.example.week1.common.database.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DataSource 프로퍼티 클래스
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/03 11:22 PM
 */
@ConfigurationProperties(prefix = "spring.datasource")
@Getter @Setter
public class DataSourceProperties {

    /** JDBC Driver Name */
    private String driverClassName;
    /** DB URL */
    private String url;
    /** DB username */
    private String username;
    /** DB pwd */
    private String password;
}
