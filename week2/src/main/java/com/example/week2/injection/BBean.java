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
@Setter
public class BBean {

    /** 1. 필드 주입 */
//    @Autowired
//    private ABean a;

    /** 2. 생성자 주입 */
    private final ABean a;
    public BBean(@Lazy ABean a) {
        this.a=a;
    }

    /** 3. Setter 주입 */
//    private ABean a;
//    @Autowired
//    public void setA(ABean a) {
//        this.a = a;
//    }

    public void aMethod() {
        a.print();
    }
    public void print() {
        System.out.println("BBean Print");
    }
}
