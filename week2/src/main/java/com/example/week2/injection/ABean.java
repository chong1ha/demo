package com.example.week2.injection;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/11 12:24 AM
 */
@Component
public class ABean {
    /**
     * 3가지 주입 방식에 대해 순환 참조 테스트
     *
     * - 상세 : 1. 필드 주입
     *         2. 생성자 주입
     *         3. setter 주입 (수정자 주입)
     *
     *         순환참조 : 서로 다른 두 개의 클래스가 서로 참조하는 상태
     *                  A가 B에 의존하고, B가 C에 의존할 때 스프링 컨테이너는 C -> B -> A 순으로 객체를 생성
     *                  그러나 A,B가 서로 의존하고 있다면 어떤 객체를 먼저 생성할지 문제가 생김
     *
     *         [결과] : 생성자 주입 시, 순환 참조 발생
     **/

    /** 1. 필드 주입 */
//    @Autowired
//    private BBean b;

    /** 2. 생성자 주입 */
    private final BBean b;
    public ABean(@Lazy BBean b) {
        this.b=b;
    }

    /** 3. Setter 주입 */
//    private BBean b;
//    @Autowired
//    public void setB(BBean b) {
//        this.b = b;
//    }


    public void bMethod() {
        b.print();
    }
    public void print() {

        System.out.println("ABean Print");
    }
}
