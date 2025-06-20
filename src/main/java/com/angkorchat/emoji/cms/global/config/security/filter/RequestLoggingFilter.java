package com.angkorchat.emoji.cms.global.config.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getMethod() + " " + request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();

        log.info("\nRequest from: {}\nRequest URI: {}\n", remoteAddr, requestURI);

        // Continue the filter chain
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
