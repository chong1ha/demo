package com.example.core.common.config;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * yaml 파일을 Spring PropertySource로 변환하는 용도
 *
 * <p>
 *      SpringBoot에서 `@TestPropertySource`를 사용할 때, yaml 파일을 프로퍼티 소스로 로드할 수 있도록 함.
 *      이 클래스를 통해 yaml 파일을 `Properties` 객체로 변환하고, 이를 Spring의 {@link PropertySource, TestPropertySource}로 사용.
 * </p>
 * <p>
 *     `@TestPropertySource` 애노테이션과 함께 사용되어, YAML 파일을 `PropertySource`로 변환함으로써
 *      테스트 환경에서도 YAML 기반의 설정을 지원
 * </p>
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/03 11:37 PM
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * 주어진 yaml 파일을 PropertySource 로 변환
     *
     * @param name 프로퍼티 소스 이름
     * @param resource yaml 파일에 대한 리소스 정보
     * @throws IOException yaml 파일 로드시 발생하는 입출력 예외
     * @return PropertySource
     */
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties propertiesFromYaml = loadYamlIntoProperties(resource);
        String sourceName = name != null ? name : resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    /**
     * 주어진 yaml 리소스 로드하여 Properties 로 변환
     *
     * @param resource yaml 파일에 대한 리소스 정보
     * @throws FileNotFoundException yaml 파일이 존재하지 않을때의 예외
     * @return Properties
     */
    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }
    }
}
