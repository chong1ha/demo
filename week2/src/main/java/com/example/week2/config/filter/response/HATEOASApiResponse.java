package com.example.week2.config.filter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HATEOAS 링크와 메타데이터 포함
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 2:28
 */
@Getter @Setter
public class HATEOASApiResponse<T> extends ApiResponse<T> {

    /** HTTP 상태 코드 */
    private int status;
    /** 응답 메시지 */
    private String message;
    /** 메타데이터 */
    private Map<String, Object> metadata;
    /** HATEOAS 링크 */
    private Map<String, String> links;

    @Builder
    public HATEOASApiResponse(T data, List<String> errors, int status, String message, Map<String, String> links, Map<String, Object> metadata) {
        super(data);
        this.setErrors(errors);
        this.status = status;
        this.message = message;
        this.links = links;
        this.metadata = metadata;
    }

    /**
     * 성공 응답 생성
     */
    public static <T> HATEOASApiResponse<T> success(T data, String message, Map<String, String> links, Map<String, Object> metadata) {
        return HATEOASApiResponse.<T>builder()
                .data(data)
                .status(200)
                .message(message)
                .links(links != null ? links : new HashMap<>())
                .metadata(metadata != null ? metadata : new HashMap<>())
                .build();
    }

    /**
     * 에러 응답 생성
     */
    public static <T> HATEOASApiResponse<T> error(List<String> errors, String message, Map<String, String> links, Map<String, Object> metadata) {

        return HATEOASApiResponse.<T>builder()
                .data(null)
                .status(400)
                .message(message)
                .links(links != null ? links : new HashMap<>())
                .metadata(metadata != null ? metadata : new HashMap<>())
                .errors(errors != null ? errors : List.of())
                .build();
    }
}
