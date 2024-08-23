package com.example.week2.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Email 및 Phone Number 입력 파라미터 형식 검증 <br>
 *
 * OncePerRequestFilter 추상클래스 상속.
 * 스프링 프레임워크 의존적.
 * 내부 로직을 보면, alreadyFilteredAttributeName을 통해 다음 요청에서 이 속성이 있는지 검사하여 필터의 중복실행 방지.
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 1:29
 */
@Log4j2
public class ValidationFilter extends OncePerRequestFilter {

    /** 정규표현식 정의 */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");

    /**
     * doFilter() : 요청 필터링
     *
     * @param request  요청 객체
     * @param response 응답 객체
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // 요청 파라미터 추출
        String email = httpRequest.getParameter("email");
        String phoneNum = httpRequest.getParameter("phoneNum");

        // 유효하지 않은 파라미터 여부 체크
        boolean hasInvalidParameter = checkForInvalidParameters(requestURI, email, phoneNum);

        // 유효하지 않은 파라미터가 있는 경우, 400 오류 응답 반환
        if (hasInvalidParameter) {
            log.error("Invalid request parameters detected for URI: {}", requestURI);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("responseCode", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("message", "Invalid request parameters.");

            response.getWriter().write(convertToJson(errorResponse));
            response.getWriter().flush();
            return;
        }

        // 필터 검증 통과한 후, 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    /**
     * 요청 URI에 따라 파라미터 유효성 검사 수행
     *
     * @return 유효하지 않은 파라미터가 있는 경우 true 반환
     */
    private boolean checkForInvalidParameters(String requestURI, String email, String phoneNum) {

        if (requestURI.startsWith("/dummy/testA")) {
            // /dummy/testA의 경우
            return isInvalidParameter(phoneNum, PHONE_NUMBER_PATTERN)
                    || isInvalidParameter(email, EMAIL_PATTERN);
        } else if (requestURI.startsWith("/dummy/testB")) {
            // /dummy/testB의 경우
            return isInvalidParameter(email, EMAIL_PATTERN);
        }
        return false;
    }

    /**
     * 파라미터 검증
     *
     * @param param 검증 파라미터 (null 가능)
     * @param pattern 정규표현식 패턴
     * @return 파라미터가 null이 아니고, 빈 문자열이 아니며, 정규표현식 패턴과 일치하지 않는 경우 true 반환
     */
    private boolean isInvalidParameter(String param, Pattern pattern) {
        return param != null && !param.trim().isEmpty() && !pattern.matcher(param).matches();
    }

    /**
     * Map을 JSON 문자열로 변환
     *
     * @param map JSON으로 변환할 Map
     */
    private String convertToJson(Map<String, Object> map) {

        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{");
        map.forEach((key, value) -> {
            jsonBuilder.append("\"").append(key).append("\":\"").append(value).append("\",");
        });

        // 마지막 쉼표 제거
        if (!map.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
