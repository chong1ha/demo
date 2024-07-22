package com.example.core.common.exception;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-07-22 오전 10:01
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        super(msg);
    }
}
