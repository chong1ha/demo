package com.example.core.common.exception;

/**
 * Service 처리 중 발생하는 예외처리
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/04 5:40 PM
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
