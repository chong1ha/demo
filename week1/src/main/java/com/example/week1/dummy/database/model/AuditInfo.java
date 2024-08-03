package com.example.week1.dummy.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * 최초 등록과 최종 수정에 관한 정보 (공통)
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-02 오후 1:12
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditInfo {

    /** 등록 일시 */
    private OffsetDateTime dummyCreateDt;
    /** 수정 일시 */
    private OffsetDateTime dummyModifyDt;
}
