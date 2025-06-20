package com.angkorchat.emoji.cms.global.config.security.config;

import com.angkorchat.emoji.cms.global.config.security.exception.handler.CustomAccessDeniedHandler;
import com.angkorchat.emoji.cms.global.config.security.exception.handler.CustomAuthenticationEntryPoint;
import com.angkorchat.emoji.cms.global.config.security.filter.JwtAuthenticationFilter;
import com.angkorchat.emoji.cms.global.config.security.filter.RequestLoggingFilter;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author junny
 * @since 1.0
 */
@Profile({"dev", "stage", "prod"})
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        this.securityConfig(http);
        this.userAntMatchers(http);
        this.permitAll(http);
        return http.build();
    }

    private void securityConfig(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(this.corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(new RequestLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    private void userAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "cms/emoji/file").permitAll()
                .requestMatchers(HttpMethod.GET, "cms/refresh/token").permitAll()
                .requestMatchers(HttpMethod.POST, "cms/login").permitAll()
                .requestMatchers(HttpMethod.POST, "studio/refresh/token").permitAll()
                .requestMatchers(HttpMethod.POST, "studio/login").permitAll());
    }

    private void permitAll(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/exception/**", "/files/**", "/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .anyRequest().authenticated());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Requested-With", "Accept", "Origin", "Set-Cookie", "Set-Cookie2"));
//        // react
//        configuration.addAllowedOriginPattern("http://localhost:[3000,3001,3002,3003,3004,3005,8095]");
//        configuration.addAllowedOriginPattern("https://localhost:[3000,3001,3002,3003,3004,3005,8095]");
//        // vite
//        configuration.addAllowedOriginPattern("http://localhost:[5173,5174,5175,5176,5178]");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin("http://localhost:3002");
        configuration.addAllowedOrigin("http://localhost:3003");
        configuration.addAllowedOrigin("http://localhost:3004");
        configuration.addAllowedOrigin("http://localhost:3005");
        configuration.addAllowedOrigin("https://localhost:3000");
        configuration.addAllowedOrigin("https://localhost:3001");
        configuration.addAllowedOrigin("https://localhost:3002");
        configuration.addAllowedOrigin("https://localhost:3003");
        configuration.addAllowedOrigin("https://localhost:3004");
        configuration.addAllowedOrigin("https://localhost:3005");
        configuration.addAllowedOrigin("http://localhost:8095");
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("http://localhost:5174");
        configuration.addAllowedOrigin("http://localhost:5175");
        configuration.addAllowedOrigin("http://localhost:5176");
        configuration.addAllowedOrigin("http://localhost:5178");
        configuration.addAllowedOrigin("https://cms-emoji-sandbox.prjt.com");
        configuration.addAllowedOrigin("https://emojistudio-sandbox.angkorlifes.com");
        configuration.addAllowedOrigin("https://emojistudio.angkorlifes.com");
        configuration.addAllowedOrigin("https://cms-api-emoji-sandbox.angkorlifes.com");
        configuration.addAllowedOrigin("https://cms-emoji-sandbox.angkorlifes.com");
        configuration.addAllowedOrigin("https://cms-emoji.angkorlifes.com");

//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Set-Cookie");
        configuration.addExposedHeader("Set-Cookie2");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
