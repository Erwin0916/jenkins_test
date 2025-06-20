package com.angkorchat.emoji.cms.global.config.security.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.UserNotRegisteredException;
import com.angkorchat.emoji.cms.global.config.security.dto.StudioLoginDto;
import com.angkorchat.emoji.cms.global.config.security.dto.StudioTokenDto;
import com.angkorchat.emoji.cms.global.config.security.dto.LoginDto;
import com.angkorchat.emoji.cms.global.config.security.dto.TokenDto;
import com.angkorchat.emoji.cms.global.config.security.exception.SecondAuthenticationFalseException;
import com.angkorchat.emoji.cms.global.constant.RequestType;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    public static final Integer ACCESS_TOKEN_EXP_TIME = 60 * 60 * 4; // 4시간
    public static final Integer REFRESH_TOKEN_EXP_TIME = 60 * 60 * 4; // 4시간
    public static final Integer STUDIO_ACCESS_TOKEN_EXP_TIME = 60 * 60 * 4; // 4시간
    public static final Integer STUDIO_REFRESH_TOKEN_EXP_TIME = 60 * 60 * 24 * 2; // 2일

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.refresh}")
    private String REFRESH_KEY;

    @Value("${jwt.studio.refresh}")
    private String STUDIO_REFRESH_KEY;
    @Value("${jwt.studio.secret}")
    private String STUDIO_SECRET_KEY;

    @Value("${jwt.prefix}")
    private String TOKEN_PREFIX;

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomStudioUserDetailsService customStudioUserDetailsService;

    private final Aes256Service aes256Service;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        REFRESH_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        STUDIO_REFRESH_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        STUDIO_SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId, boolean secondAuth) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("isSecondAuthenticated", secondAuth);

        return String.format("%s%s", TOKEN_PREFIX, Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXP_TIME)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact());
    }

    public String createRefreshToken(String userId, boolean secondAuth) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("isSecondAuthenticated", secondAuth);

        return String.format("%s", Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXP_TIME)))
                .signWith(SignatureAlgorithm.HS256, REFRESH_KEY)
                .compact());
    }

    public String createStudioToken(String angkorId, Integer artistId, boolean secondAuth) {
        Claims claims = Jwts.claims().setSubject(angkorId);
        claims.put("artistId", artistId);
        claims.put("isSecondAuthenticated", secondAuth);

        return String.format("%s%s", TOKEN_PREFIX, Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusSeconds(STUDIO_ACCESS_TOKEN_EXP_TIME)))
                .signWith(SignatureAlgorithm.HS256, STUDIO_SECRET_KEY)
                .compact());
    }

    public String createStudioRefreshToken(String angkorId, String uuid, String sessionId, Integer artistId, boolean secondAuth) {
        Claims claims = Jwts.claims().setSubject(angkorId);
        claims.put("isSecondAuthenticated", secondAuth);
        claims.put("isUserSignedUp", secondAuth);
        claims.put("artistId", artistId);
        claims.put("uuid", uuid);
        claims.put("sessionId", sessionId);

        return String.format("%s", Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusSeconds(STUDIO_REFRESH_TOKEN_EXP_TIME)))
                .signWith(SignatureAlgorithm.HS256, STUDIO_REFRESH_KEY)
                .compact());
    }

    public TokenDto createAccessTokenDto(LoginDto admin, boolean secondAuth) {
        return TokenDto.create(aes256Service, admin, this.createToken(admin.getLoginId(), secondAuth));
    }

    public StudioTokenDto createStudioAccessTokenDto(StudioLoginDto admin, boolean secondAuth) {

        return StudioTokenDto.create(aes256Service, admin, this.createStudioToken(admin.getAngkorId(), admin.getId(), secondAuth));
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.parseClaims(token);
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Authentication getStudioAuthentication(String token) {
        Claims claims = this.parseStudioClaims(token);
        UserDetails userDetails = this.customStudioUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims parseClaims(String token) {
        token = this.extractToken(token);

        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Claims parseCmsRefreshClaims(String token) {
        token = this.extractToken(token);

        return Jwts.parser().setSigningKey(REFRESH_KEY).parseClaimsJws(token).getBody();
    }

    public Claims parseStudioClaims(String token) {
        token = this.extractToken(token);

        return Jwts.parser().setSigningKey(STUDIO_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Claims parseStudioRefreshClaims(String token) {
        token = this.extractToken(token);

        return Jwts.parser().setSigningKey(STUDIO_REFRESH_KEY).parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public String requestTypeCheck(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        String[] parts = requestUri.split("/");

        // 대행사 CMS 요청
        if (RequestType.STUDIO.getType().equals(parts[1])) {
            return RequestType.STUDIO.getType();
        } else if (RequestType.EXCEPTION.getType().equals(parts[1])) {
            return RequestType.EXCEPTION.getType();
        }

        return RequestType.CMS.getType();
    }

    public boolean validateToken(HttpServletRequest request, String token) {
        String requestUri = request.getRequestURI();

        if(requestUri.equals("/cms/refresh/token")) {
            try{
                parseClaims(token);
            } catch (ExpiredJwtException e) {
                return true;
            }
        }

        Claims claims = parseClaims(token);

        // 2차 인증 전 사용가능 request 리스트
        List<String> permitReqList = new java.util.ArrayList<>(List.of("/cms/check/*", "/cms/auth/code", "/cms/auth/check"));

        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean isPermitted = permitReqList.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestUri));

        if (!isPermitted && !claims.get("isSecondAuthenticated", Boolean.class)) {
            throw new SecondAuthenticationFalseException();
        }

        return true;
    }

    public boolean validateStudioToken(HttpServletRequest request, String token) {
        String requestUri = request.getRequestURI();

        if(requestUri.equals("/studio/refresh/token")) {
            try{
                parseStudioClaims(token);
            } catch (ExpiredJwtException e) {
                return true;
            }
        }

        Claims claims = parseStudioClaims(token);

        // 2차 인증 전 사용가능 request 리스트
        List<String> permitReqList = new java.util.ArrayList<>(List.of("/studio/check/*", "/studio/auth/code", "/studio/auth/check"));

        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean isPermitted = permitReqList.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestUri));

        if (!isPermitted && !claims.get("isSecondAuthenticated", Boolean.class)) {
            throw new SecondAuthenticationFalseException();
        }

        permitReqList.add("/studio/signup");
        // 리프레시 토큰은 2차인증자만 가능하게 하여 미가입자는 다시 로그인페이지로 돌린다.
        //permitReqList.add("/studio/refresh/token");
        permitReqList.add("/studio/file");
        permitReqList.add("/studio/term/list");

        isPermitted = permitReqList.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestUri));

        if (!isPermitted && claims.get("artistId", Integer.class).equals(0)) {
            throw new UserNotRegisteredException();
        }

        return true;
    }

    private String extractToken(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) throw new IllegalArgumentException();
        return token.substring(TOKEN_PREFIX.length());
    }
}
