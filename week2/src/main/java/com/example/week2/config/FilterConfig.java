package com.example.week2.config;

import com.example.week2.config.filter.LoggingFilter;
import com.example.week2.config.filter.ValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Context의 Bean으로 필터(서블릿 필터) 등록
 *
 * @author
 * @version 1.0
 * @since 2024-08-23 오후 1:29
 */
@Configuration
public class FilterConfig {

    /**
     * 'ValidationFilter' 등록하는 Bean 생성
     */
    @Bean
    public FilterRegistrationBean<ValidationFilter> validationFilterRegistration() {
        FilterRegistrationBean<ValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ValidationFilter());
        registrationBean.addUrlPatterns("/dummy/testA", "/dummy/testB");  // 필터를 적용할 URL 패턴 설정
        registrationBean.setName("validationFilter");
        registrationBean.setOrder(1);  // 필터의 순서 설정 (필터 Chaining)
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("*");
        registrationBean.setName("loggingFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
