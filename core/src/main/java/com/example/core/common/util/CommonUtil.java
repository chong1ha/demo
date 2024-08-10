package com.example.core.common.util;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    /**
     * HTTP 요청
     *
     * @param url 요청을 보낼 URL
     * @param method HTTP 메소드 (GET, POST 등)
     * @param body 요청 본문 데이터 (POST 일 경우 사용, GET 요청은 null)
     * @param headers 요청에 사용할 헤더 (null 가능, 없을 시 기본 헤더 설정)
     * @throws Exception 요청 전송 중 발생한 예외
     * @return ResponseEntity
     */
    private static <T> ResponseEntity<String> sendRequest(String url,
                                                          HttpMethod method,
                                                          T body,
                                                          HttpHeaders headers) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        try {
            HttpEntity<T> requestEntity = new HttpEntity<>(body, headers);

            // HTTP 요청 전송
            responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) { // 200:ok
                return responseEntity;
            } else {
                throw new Exception("HTTP Error, Status Code : " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
           throw e;
        }
    }

    /**
     * `application/x-www-form-urlencoded` 형식의 HTTP 요청
     *
     * @param url 요청을 보낼 URL
     * @param method HTTP 메소드 (GET, POST 등)
     * @param parameters 요청에 사용할 파라미터 (POST 일 경우, 사용)
     * @param headers 요청에 사용할 헤더 (필요한 경우 사용, null 가능)
     * @throws Exception 요청 전송 중 발생한 예외
     * @return ResponseEntity
     */
    private static ResponseEntity<String> sendFormUrlEncodedRequest(String url,
                                                                    HttpMethod method,
                                                                    MultiValueMap<String, String> parameters,
                                                                    HttpHeaders headers) throws Exception {

        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        return sendRequest(url, method, parameters, headers);
    }

    /**
     * POST 요청 (폼 데이터 전송용)
     *
     * @param url 요청을 보낼 URL
     * @param parameters 요청에 사용할 폼 데이터 파라미터
     * @throws Exception 요청 전송 중 발생한 예외
     * @return ResponseEntity
     */
    public static ResponseEntity<String> sendPostRequest(String url,
                                                         MultiValueMap<String, String> parameters) throws Exception {
        return sendFormUrlEncodedRequest(url, HttpMethod.POST, parameters, null);
    }

    /**
     * GET 요청 전송 (폼 데이터 전송용)
     *
     * @param url 요청을 보낼 URL
     * @throws Exception 요청 전송 중 발생한 예외
     * @return ResponseEntity
     */
    public static ResponseEntity<String> sendGetRequest(String url) throws Exception {
        return sendFormUrlEncodedRequest(url, HttpMethod.GET, null, null);
    }

    /**
     * JSON 형식 POST 요청
     *
     * @param url 요청을 보낼 URL
     * @param json JSON 형식의 요청 본문
     * @return ResponseEntity
     */
    public static ResponseEntity<String> sendJsonRequest(String url, String json) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return sendRequest(url, HttpMethod.POST, json, headers);
    }

    /**
     * XML 형식의 POST 요청
     *
     * @param url 요청을 보낼 URL
     * @param xml XML 형식의 요청 본문
     * @return ResponseEntity
     */
    public static ResponseEntity<String> sendXmlRequest(String url, String xml) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        return sendRequest(url, HttpMethod.POST, xml, headers);
    }
}
