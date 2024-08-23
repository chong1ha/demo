package com.example.week2.config.filter.controller;

import com.example.week2.config.filter.response.DetailedApiResponse;
import com.example.week2.config.filter.response.HATEOASApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 더미용 컨트롤러
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 1:52
 */
@Log4j2
@RestController
@RequestMapping("/dummy")
public class DummyController {

    /**
     * 정상: http://localhost:8083/dummy/testA?email=test@example.com&phoneNum=010-1234-5678
     * 비정상: http://localhost:8083/dummy/testA?email=invalid-email&phoneNum=12345678
     */
    @RequestMapping(value = "/testA", method = RequestMethod.GET)
    public ResponseEntity<DetailedApiResponse<String>> getTestA(
            @RequestParam(name = "email") String email
            , @RequestParam(name = "phoneNum", required = false) String phoneNum
    ) throws Exception {

        try {
            String result = "TestA Result";

            DetailedApiResponse<String> response = DetailedApiResponse.success(result, "Operation Successful");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {

            log.error("Error occurred in getTestA method", e);

            // 에러 응답 생성
            DetailedApiResponse<String> errorResponse = DetailedApiResponse.error(
                    Collections.singletonList("An unexpected error occurred. Please try again later."),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error in getTestA"
            );

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 정상: http://localhost:8083/dummy/testB?email=test@example.com
     * 비정상: http://localhost:8080/dummy/testB
     */
    @RequestMapping(value = "/testB", method = RequestMethod.GET)
    public ResponseEntity<HATEOASApiResponse<String>> getTestB(
            @RequestParam(name = "email") String email
    ) throws Exception {

        String result = "TestB Result";

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("timestamp", System.currentTimeMillis());

        Map<String, String> links = new HashMap<>();
        links.put("self", "/dummy/testB?email=" + email);
        links.put("testA", "/dummy/testA?email=" + email + "&phoneNum=" + "010-0000-1111");

        // 성공 응답 생성
        HATEOASApiResponse<String> response = HATEOASApiResponse.success(
                result,
                "Operation Successful with HATEOAS",
                links,
                metadata
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
