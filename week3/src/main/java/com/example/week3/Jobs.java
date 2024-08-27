package com.example.week3;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-27 오후 1:22
 */
@Getter
@Setter
public class Jobs {

    private Map<String, JobStep> steps;

    @Override
    public String toString() {
        return "Jobs{" +
                "steps=" + steps +
                '}';
    }
}
