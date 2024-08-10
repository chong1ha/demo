package com.example.week2;

import com.example.week2.injection.ABean;
import com.example.week2.injection.BBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author kang-geonha
 * @version 1.0
 * @since 2024/08/11 12:21 AM
 */
@Component
@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {

    private final ABean a;
    private final BBean b;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 3가지 주입 - 순환참조 테스트
         *   - 상세 : [테스트] 서로의 메소드를 순환 호출
         *            [결과] 생성자 주입 시에만, 순환참조 발생
         *
         *            [전제] 해당 객체가 메모리에 적재된 후에 빈을 주입
         *            그러나 생성자 주입 방식 ->
         *                           객체를 생성자로 생성하는 시점에 필요한 빈들을 의존 주입
         *                           즉, 객체 생성하는 동시에 빈 주입
         *            [차이] 객체를 이미 생성한 후에 주입 != 객체 생성과 동시에 주입
         *
         *            [결론] 그렇다면 세터 or 필드 주입을 사용하는 가? No
         *            객체 설게에 대한 문제일 뿐
         *
         *            [설계] 생성자 주입과 다르게 필드 주입은 final 선언 x => 객체가 변할 수 있다.(불변성이 깨질 수 있다)
         *                   생성자 주입 => 순환 의존성을 가질 경우, BeanCurrentlyCreationException을 발생 시킴으로 써 순환 의존성을 알 수 있다.
         *                   스프링 4.3부터 의존성 주입으로부터 클래스를 완벽하게 분리 가능
         *
         *                   필드 주입 방식의 단점
         *                   1. 단일 책임의 원칙 위반
         *                   2. 의존성 숨는다.
         *                   3. 불변성 x
         *
         *             [해결] @Lazy, @PostConstruct+Setter
         **/
        a.bMethod();
        b.aMethod();
    }
}
