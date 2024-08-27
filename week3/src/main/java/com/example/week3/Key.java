package com.example.week3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-27 오전 10:06
 */
@Getter
@RequiredArgsConstructor
public enum Key {

    ON("on"),
    SCHEDULER("scheduler"),
    NAME("name"),
    CONFIG("config"),
    CLASS("class"),
    RUNS("runs");

    private final String key;
}
