package com.example.week1.dummy.database.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * DummyDomain Model
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/21 11:41 PM
 */
@Getter @Setter
@NoArgsConstructor
public class DummyDomain extends AuditInfo {

    /** Id */
    private String dummyId;
    /** Name */
    private String dummyName;
    /** Desc */
    private String dummyDesc;
    /** Service exec Y/N */
    private char dummyServiceYn;


    /**
     * DummyDomain의 Builder 클래스
     */
    @Builder
    public DummyDomain(String dummyId, String dummyName, char dummyServiceYn,
                       OffsetDateTime dummyCreateDt, OffsetDateTime dummyModifyDt) {
        super(dummyCreateDt, dummyModifyDt);
        this.dummyId = dummyId;
        this.dummyName = dummyName;
    }
}
