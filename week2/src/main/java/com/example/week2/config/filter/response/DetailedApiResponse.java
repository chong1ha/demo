package com.example.week2.config.filter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * 상태 코드와 메시지 포함
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 2:23
 */
@Getter @Setter
public class DetailedApiResponse<T> extends ApiResponse<T> {

    /** HTTP 상태 코드 */
    private int status;
    /** 응답 메시지 */
    private String message;

    @Builder
    public DetailedApiResponse(T data, List<String> errors, HttpStatus status, String message) {
        super(data);
        this.setErrors(errors);
        this.status = status.value();
        this.message = message;
    }

    /**
     * 성공 응답 생성
     */
    public static <T> DetailedApiResponse<T> success(T data, String message) {

        return DetailedApiResponse.<T>builder()
                .data(data)
                .status(HttpStatus.OK)
                .message(message)
                .build();
    }

    /**
     * 에러 응답 생성
     */
    public static <T> DetailedApiResponse<T> error(List<String> errors, HttpStatus status, String message) {

        return DetailedApiResponse.<T>builder()
                .errors(errors)
                .status(status)
                .message(message)
                .build();
    }
}
