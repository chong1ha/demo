package com.example.core.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 공통 유틸리티 클래스
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오전 9:57
 */
public class CommonUtil {

    /**
     * get milliseconds from LocalDateTime
     *
     * @param localDateTime: 메시지 생성시간, LocalDateTime.now()
     * @return Type Long
     */
    public static Long localDateToLong(LocalDateTime localDateTime) {
        return ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
