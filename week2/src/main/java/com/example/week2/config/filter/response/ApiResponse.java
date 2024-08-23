package com.example.week2.config.filter.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 2:15
 */
@Getter @Setter
@RequiredArgsConstructor
public abstract class ApiResponse<T> {

    /** 응답 데이터 */
    @NonNull
    private T data;
    /** 에러 메시지 */
    private List<String> errors;


    /**
     * 응답 성공 여부
     */
    public boolean isSuccess() {
        return errors == null || errors.isEmpty();
    }
}
