package com.example.week1;

import com.example.week1.test01.workflow.JobScheduler01;
import com.example.week1.test02.workflow.JobScheduler02;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 스케줄러 테스트 1 (EnableScheduling) : {@link JobScheduler01} <br>
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-04 오후 1:26
 */
@SpringBootApplication
public class Application {

    /**
     * Framework : SpringBoot 3.3.1 (Spring 6.x) <br>
     * Build Tool : Maven <br>
     * JAVA 17
     */
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

//        ApplicationContext context = SpringApplication.run(Application.class, args);
//        JobScheduler02 jobScheduler = context.getBean(JobScheduler02.class);
//
//        try {
//            // 작업 스케줄러 시작
//            jobScheduler.startJobs();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}