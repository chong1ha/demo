package com.example.core.common.util;

import java.time.*;

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

    /**
     * get OffsetDateTime from Long
     *
     * @param time 타임 long값
     * @throws IllegalArgumentException 표준 시간대를 잘못 설정할 경우, OffsetDateTime 변환 실패할 경우
     * @return offsetDateTime
     */
    public static OffsetDateTime longToOffsetDateTime(long time) {

        // 추후 환경변수로 추출
        String zoneIdStr = "Asia/Seoul";

        ZoneId zone;
        try {
            // 주어진 시간대 문자열 체크
            zone = ZoneId.of(zoneIdStr);
        } catch (Exception e) {
            String msg = String.format(
                    "Invalid zoneIdStr: %s. Available zoneIdStr are: %s",
                    zoneIdStr,
                    String.join(", ", ZoneId.getAvailableZoneIds()) // 올바른 ZoneIdStr을 msg로 보여줌
            );
            throw new IllegalArgumentException(msg, e);
        }

        // OffsetDateTime으로 변환
        try {
            return Instant.ofEpochMilli(time).atZone(zone).toOffsetDateTime();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert time to OffsetDateTime", e);
        }
    }
}
