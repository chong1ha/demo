package com.example.week1.database.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * MyBatis 설정
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/22 12:08 AM
 */
@Configuration
@MapperScan(basePackages = {
        "com.example.week1.database.common.mapper",
        "com.example.week1.database.dummy.mapper"
})
@EnableTransactionManagement
@EnableConfigurationProperties(MyBatisProperties.class)
public class MyBatisConfig {

    /**
     * MyBatis의 프로퍼티를 외부 설정 파일에서 읽어옴
     *
     * @return MyBatisProperties
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public MyBatisProperties myBatisProperties() {
        return new MyBatisProperties();
    }

    /**
     * SqlSessionFactory 생성
     *
     * @param dataSource 애플리케이션의 데이터 소스
     * @param myBatisProperties MyBatis 설정을 담고 있는 프로퍼티 객체
     * @throws Exception SQL 세션 팩토리 생성 중 발생할 수 있는 예외
     * @return SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MyBatisProperties myBatisProperties) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(myBatisProperties.getMapperLocations()));
        sessionFactory.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());
        return sessionFactory.getObject();
    }

    /**
     * SqlSessionTemplate 생성
     *
     * @param sqlSessionFactory SqlSessionFactory
     * @return SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 트랜잭션 매니저 생성
     *
     * @param dataSource 애플리케이션의 데이터 소스
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
