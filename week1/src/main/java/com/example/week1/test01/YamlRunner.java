package com.example.week1.test01;

/**
 * 테스트: 순수 자바환경에서의 동적 클래스 로딩
 *
 * @author gunha
 * @version 1.0
 * @since 2024-07-15 오후 2:42
 */
public class YamlRunner {

    static {
        System.out.println("YamlRunner static block executed.");
    }

    public void execute() {
        System.out.println("YamlRunner execute method called.");
    }
}
