package com.example.week1.dummy.domain.model;

/**
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/22 12:17 AM
 */
public class DummyData {

    private static final DummyDomain DUMMY_DOMAIN = DummyDomain.builder()
            .id("testId")
            .hostId("hostId")
            .dockerId("dockerId")
            .build();

    public static DummyDomain getDummyDomain() {
        return DUMMY_DOMAIN;
    }
}
