package com.example.week2.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * HTTP 요청에 대한 로깅 <br>
 *
 * Filter 인터페이스 구현.
 * 서블릿 기반.
 *
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 1:29
 */
@Log4j2
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Logging2Filter init()");
        Filter.super.init(filterConfig);
    }

    /**
     * doFilter() 요청 가로채기
     */
    @Override
    public void doFilter(
            // ServletRequest 인터페이스는 HTTP 프로토콜에 국한되지 않고, 다양한 프로토콜 요청 처리 가능
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        // HttpServletReuqest로 캐스팅하여 사용 (HTTP 특화)
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        log.info("LoggingFilter Request URL: {}", httpRequest.getRequestURL());
        log.info("LoggingFilter Request Method: {}", httpRequest.getMethod());

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("LoggingFilter destroy()");
        Filter.super.destroy();
    }
}
