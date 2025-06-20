package com.angkorchat.emoji.cms.global.config.security.filter;


import com.angkorchat.emoji.cms.domain.angkor.auth.exception.UserNotFoundException;
import com.angkorchat.emoji.cms.domain.angkor.auth.exception.UserNotRegisteredException;
import com.angkorchat.emoji.cms.global.config.security.constant.JwtProperties;
import com.angkorchat.emoji.cms.global.config.security.exception.SecondAuthenticationFalseException;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import com.angkorchat.emoji.cms.global.constant.RequestType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author junny
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        try {
            this.validateToken(request, response);
        } catch (SecondAuthenticationFalseException ex) {
            log.error("2차인증 되지 않은 사용자 입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.SECOND_AUTHENTICATION_FALSE_CODE);
        } catch (UserNotRegisteredException ex) {
            log.error("가입처리 되지 않은 사용자입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.UNREGISTERED_USER_CODE);
        } catch (SecurityException | MalformedJwtException | UserNotFoundException e) {
            log.error("잘못된 Jwt 서명입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.TOKEN_INVALID_EXCEPTION);
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.EXPIRED_ACCESS_TOKEN_EXCEPTION_CODE);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.TOKEN_INVALID_EXCEPTION);
        } catch (IllegalArgumentException ex) {
            log.error("잘못된 토큰입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.TOKEN_INVALID_EXCEPTION);
        }

        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtProvider.resolveToken(request);

        if (token != null) {
            String cmsType = jwtProvider.requestTypeCheck(request);

            if (RequestType.STUDIO.getType().equals(cmsType)) {
                if (jwtProvider.validateStudioToken(request, token)) {
                    Authentication authentication = jwtProvider.getStudioAuthentication(token);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else if (RequestType.CMS.getType().equals(cmsType)) {
                if (jwtProvider.validateToken(request, token)) {
                    Authentication authentication = jwtProvider.getAuthentication(token);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
    }
}
