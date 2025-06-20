package com.angkorchat.emoji.cms.global.config.security.util;

import com.angkorchat.emoji.cms.global.util.CommonUtils;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtils {
    public static ResponseCookie setRefreshCookie(String refreshToken, Integer expTm) {
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")     // 필요 시 경로 지정
                .maxAge(Duration.ofSeconds(expTm))
                .sameSite("None") // 또는 "Lax", 상황에 따라 설정
                .build();

        CommonUtils.infoLogging("리프레시 쿠키 세팅");

        return refreshCookie;
    }

    public static ResponseCookie setAccessCookie(String accessToken, Integer expTm) {
        ResponseCookie accessCookie = ResponseCookie.from("Authorization", accessToken)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")     // 필요 시 경로 지정
                        .maxAge(Duration.ofSeconds(expTm))
                        .sameSite("None") // 또는 "Lax", 상황에 따라 설정
                        .build();

        CommonUtils.infoLogging("엑세스 쿠키 세팅");

        return accessCookie;
    }
}