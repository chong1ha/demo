package com.example.week3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-27 오후 1:23
 */
@Getter
@Setter
public class Job {

    private String name;
    private String className;

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
