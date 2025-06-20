package com.angkorchat.emoji.cms.global.config.security.config;

import com.angkorchat.emoji.cms.global.config.security.exception.handler.CustomAccessDeniedHandler;
import com.angkorchat.emoji.cms.global.config.security.exception.handler.CustomAuthenticationEntryPoint;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ContentSecurityPolicyHeaderWriter;


/**
 * @author junny
 * @since 1.0
 */
@Profile("local")
@Configuration
public class LocalSecurityConfig extends SecurityConfig {
    public LocalSecurityConfig(
            JwtProvider jwtProvider,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler) {
        super(jwtProvider, customAuthenticationEntryPoint, customAccessDeniedHandler);
    }

    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.addHeaderWriter(new ContentSecurityPolicyHeaderWriter("frame-ancestors 'self' localhost")))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/**").permitAll() // 모든 요청 허용
                );
        return super.securityFilterChain(http);
    }
}
