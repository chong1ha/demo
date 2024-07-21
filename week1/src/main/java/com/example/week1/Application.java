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
public class Application implements CommandLineRunner {

    /**
     * class-name <br>
     *   - volatile 키워드를 통해 동시성 문제 해결 <br>
     *      - CPU별(쓰레드별) CPU cache가 아닌 Main Memory에 저장하겠다는 의미 <br>
     *      - 읽을 때에는 상관없지만, 여러 쓰레드가 write하는 상황에서는 synchronized가 필요
     */
    private volatile String className;
    /** yaml 경로 */
    private static final String YAML_FILE_PATH = "application.yaml";

    /**
     * Framework : SpringBoot 3.3.1 (Spring 6.x) <br>
     * Build Tool : Maven <br>
     * JAVA 17
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Main method <br>
     *
     * 애플리케이션 실행 이후, yaml 파일에서 클래스 이름을 읽어와 해당 클래스를 동적으로 로드하여 실행 <br>
     * case: 내부 리소스, 외부 파일로의 실행
     *
     * @param args
     */
    @Override
    public void run(String... args) {

        Yaml yaml = new Yaml();

        // 택1. 내부 리소스일 경우
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(YAML_FILE_PATH)) {

            if (in == null) {
                throw new IOException("application.yaml not found");
            }

            // YAML 파일 파싱 및 클래스 이름 저장
            Map<String, Object> obj = yaml.load(in);
            className = ((Map<String, String>) obj.get("week1")).get("class");

            // 클래스 로딩 및 실행
            executeClass();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 택2. 외부 파일인 경우
        try (InputStream in = new FileInputStream(EXT_YAML_FILE_PATH)) {

            // YAML 파일 파싱 및 클래스 이름 저장
            Map<String, Object> obj = yaml.load(in);
            className = ((Map<String, String>) obj.get("week1")).get("class");

            // 클래스 로딩 및 실행
            executeClass();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * 클래스를 동적으로 로드하고 실행
     */
    private synchronized void executeClass() {

        try {
            // 클래스 로드
            Class<?> clazz = Class.forName(className);
            System.out.println("Class " + className + " loaded successfully.");

            // 클래스 인스턴스 생성
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // execute 메서드 호출
            clazz.getMethod("execute").invoke(instance);
        } catch (ReflectiveOperationException e) {
            System.out.println("Error executing class: " + e.getMessage());
        }
    }
}