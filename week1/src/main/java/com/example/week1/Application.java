package com.example.week1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
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
    }
}