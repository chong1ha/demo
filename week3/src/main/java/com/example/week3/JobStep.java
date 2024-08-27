package com.example.week3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-27 오후 1:22
 */
@Getter
@Setter
public class JobStep {

    private List<Job> runs;
    private String needs;

    @Override
    public String toString() {
        return "JobStep{" +
                "runs=" + runs +
                ", needs='" + needs + '\'' +
                '}';
    }
}
