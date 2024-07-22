package com.example.week1.database.dummy.model;

import lombok.Builder;
import lombok.Getter;

/**
 * DummyDomain Model
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/21 11:41 PM
 */
@Getter
public class DummyDomain {

    private String id;
    private String hostId;
    private String dockerId;

    @Builder
    public DummyDomain(String id, String hostId, String dockerId) {
        this.id = id;
        this.hostId = hostId;
        this.dockerId = dockerId;
    }
}
