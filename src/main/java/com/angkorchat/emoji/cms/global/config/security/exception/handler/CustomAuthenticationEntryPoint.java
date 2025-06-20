package com.angkorchat.emoji.cms.global.config.security.exception.handler;

import com.angkorchat.emoji.cms.global.config.security.constant.JwtProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author junny
 * @since 1.0
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        Object jwtException = request.getAttribute(JwtProperties.JWT_EXCEPTION);

        if (jwtException == null) {
            response.sendRedirect("/exception/authentication");
        } else if (((int) jwtException) == JwtProperties.EXPIRED_ACCESS_TOKEN_EXCEPTION_CODE) {
            response.sendRedirect("/exception/expired_access_token");
        } else if (((int) jwtException) == JwtProperties.TOKEN_INVALID_EXCEPTION) {
            response.sendRedirect("/exception/token_invalid_token");
        } else if (((int) jwtException) == JwtProperties.SECOND_AUTHENTICATION_FALSE_CODE) {
            response.sendRedirect("/exception/second_auth_false");
        } else if (((int) jwtException) == JwtProperties.UNREGISTERED_USER_CODE) {
            response.sendRedirect("/exception/unregistered_user");
        }
    }
}
