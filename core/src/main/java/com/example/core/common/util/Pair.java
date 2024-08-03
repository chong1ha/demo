package com.example.core.common.util;

import lombok.Getter;

/**
 * Custom Pair 클래스 <br>
 *
 * 두 값 저장 및 접근 가능한 데이터 구조 제공
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-02 오후 12:58
 */
@Getter
public class Pair<A, B> {

    /** 첫번째 요소 */
    private A first;
    /** 두번째 요소 */
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
