package com.example.core.common.util;

import java.util.List;

/**
 * 리스트나 컬렉션 유틸리티 클래스
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-02 오후 12:57
 */
public class CollectionUtil {

    /**
     * 주어진 리스트가 null이거나 비어 있는지 확인
     *
     * @param list
     * @return null이거나 비어있으면 true
     */
    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
