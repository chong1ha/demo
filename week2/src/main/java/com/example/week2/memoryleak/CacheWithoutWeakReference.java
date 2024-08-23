package com.example.week2.memoryleak;

import java.util.HashMap;
import java.util.Map;

/**
 * 메모리 누수 확인 <br>
 *
 * 추가. VisualVM을 통해 모니터링 수행
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 3:13
 */
public class CacheWithoutWeakReference {

    /** 멤버변수이자 컬렉션에서 누수가 많이 발생 (예시. 캐시 맵) */
    private static final Map<String, Object> cache = new HashMap<>();

    /**
     * 캐시에 객체 저장
     */
    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 캐시에서 객체 조회
     */
    public static Object get(String key) {
        return cache.get(key);
    }

    /**
     * 실행부
     */
    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {
            // 대량의 LargeObject 인스턴스를 캐시에 추가
            put("key" + i, new LargeObject());
        }

        System.out.println("Cache size: " + cache.size());

        // 캐시에 저장된 모든 객체 참조를 제거
        cache.clear();

        // 강제로 가비지 컬렉션을 호출하여 객체가 메모리에서 제거되도록 함
        System.gc();

        System.out.println("Cache size after GC: " + cache.size());
    }

    /**
     * largeObject
     */
    static class LargeObject {
        private final byte[] data = new byte[1024 * 1024]; // 1MB
    }
}
