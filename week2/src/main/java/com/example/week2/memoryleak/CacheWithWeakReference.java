package com.example.week2.memoryleak;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 메모리 누수 해결방법 <br>
 *
 * WeakReference로 객체에 대해 강한 참조를 유지하지 않음
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 3:14
 */
public class CacheWithWeakReference {

    /** value: WeakReference */
    private static final Map<String, WeakReference<Object>> cache = new HashMap<>();

    /**
     * 캐시에 객체 저장
     */
    public static void put(String key, Object value) {
        cache.put(key, new WeakReference<>(value));
    }

    /**
     * 캐시에서 객체 조회
     */
    public static Object get(String key) {
        WeakReference<Object> reference = cache.get(key);
        return (reference != null) ? reference.get() : null;
    }

    /**
     * 실행부
     */
    public static void main(String[] args) {

        for (int i = 0; i < 1000000; i++) {
            // 대량의 LargeObject 인스턴스를 캐시에 추가
            put("key" + i, new LargeObject());
        }

        System.out.println("Cache size: " + cache.size());

        // 캐시에 저장된 모든 객체 참조를 제거
        cache.clear();

        // 강제로 가비지 컬렉션을 호출하여 객체가 메모리에서 제거되도록 함
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Cache size after GC: " + cache.size());
    }

    /**
     * largeObject
     */
    static class LargeObject {
        private final byte[] data = new byte[1024 * 1024]; // 1MB
    }
}
