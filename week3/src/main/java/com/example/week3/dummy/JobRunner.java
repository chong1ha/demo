package com.example.week3.dummy;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-26 오후 5:47
 */
@Component
public class JobRunner {

    @Scheduled(cron = "*/20 * * * * *")
    public void run() {
        System.out.println("JobRunner is running...");
    }
}
