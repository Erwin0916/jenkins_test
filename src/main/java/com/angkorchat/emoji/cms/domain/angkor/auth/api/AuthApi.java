package com.angkorchat.emoji.cms.domain.angkor.auth.api;

import com.angkorchat.emoji.cms.domain.angkor.auth.dto.request.CmsLogin;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.request.CmsPassword;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsAuthenticate;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsLoginInfo;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsTokenDto;
import com.angkorchat.emoji.cms.domain.angkor.auth.service.AuthService;
import com.angkorchat.emoji.cms.global.config.security.dto.LoginTokenDto;
import com.angkorchat.emoji.cms.global.config.security.dto.TokenDto;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import com.angkorchat.emoji.cms.global.config.security.util.CookieUtils;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;

@Tag(name = "CMS Auth API", description = "CMS 로그인 인증을 위한 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("cms")
public class AuthApi {
    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthApi.class);

    @Operation(summary = "로그인", description = "앙코르 Emoji CMS 로그인을 진행한다.", tags = "CMS Auth API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(HttpServletRequest req, @RequestBody @Valid CmsLogin request) {
        ResponseEntity<LoginTokenDto> res;

        res = ResponseEntity.ok(authService.login(request));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String resString = objectMapper.writeValueAsString(res.getBody());

            log.info("login data ________________: {}", resString);
        } catch (JsonProcessingException e) {
            log.error("res data json parsing error");
        }

        // 세션정보 조회하여 세션스토리지 쿠키로 만들기
        HttpSession session = req.getSession();
        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(Objects.requireNonNull(res.getBody()).getRefreshToken(), JwtProvider.REFRESH_TOKEN_EXP_TIME);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok().headers(headers).body(res.getBody().getAccessTokenDto());
    }

    // ToDo 호출횟수 제한 필요함.
    @Operation(summary = "Refresh Token 재발급", description = "Access Token 이 만료되었을 때 Refresh Token 으로 AccessToken/RefreshToken 을 재발급 한다.", tags = "CMS Auth API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(HttpServletRequest req, @Parameter(name = "refreshToken", hidden = true) @CookieValue(value = "refreshToken") String refreshToken) {
        CmsTokenDto res = authService.refreshToken(refreshToken);

        // 세션정보 조회하여 세션스토리지 쿠키로 만들기
        HttpSession session = req.getSession();
        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(res.getRefreshToken(), JwtProvider.REFRESH_TOKEN_EXP_TIME);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        headers.set(HttpHeaders.AUTHORIZATION,res.getAccessToken());

        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "input AngkorChat UserAngkorID", description = "2차인증에 UserAngkorID 를 입력받아 존재여부를 확인한다.\n\n입력할 id 종류는 UserAngkorID 이다.", tags = "CMS Auth API")
    @GetMapping("/check/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> checkAngkorId(@Parameter(name = "id", description = "AngkorChat UserAngkorID") @PathVariable(name = "id") String id) {
        authService.checkAngkorId(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "request Authenticate by UserAngkorID", description = "AngkorChat 으로 2차인증을 위한 인증번호를 전송한다.(UserAngkorID)", tags = "CMS Auth API")
    @PostMapping("/auth/code")
    @ResponseStatus(HttpStatus.OK)
    public CmsAuthenticate sendAuthenticateCodeByUserId(@Parameter(name = "id", description = "AngkorChat UserAngkorID") @RequestParam(name = "id") String id) {
        return authService.sendAuthenticateCode(id);
    }

    @Operation(summary = "check Authenticate", description = "인증번호를 입력받아 2차인증을 진행한다.", tags = "CMS Auth API", parameters = {
            @Parameter(name = "authKey", description = "2차 인증키", required = true),
            @Parameter(name = "authNumber", description = "2차 인증번호", required = true),
            @Parameter(name = "userAngkorId", description = "AngkorChat UserAngkorID", required = true)
    })
    @GetMapping("/auth/check")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDto> checkAuthentication(HttpServletRequest req,
                                                        @RequestParam(name = "authKey") String authKey,
                                                        @RequestParam(name = "authNumber") String authNumber,
                                                        @RequestParam(name = "userAngkorId") String userAngkorId) {
        LoginTokenDto res = authService.checkAuthentication(authKey, authNumber, userAngkorId);

        // 세션정보 조회하여 세션스토리지 쿠키로 만들기
        HttpSession session = req.getSession();
        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(res.getRefreshToken(), JwtProvider.REFRESH_TOKEN_EXP_TIME);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok().headers(headers).body(res.getAccessTokenDto());
    }

    @Operation(summary = "로그인 정보", description = "앙코르 Emoji CMS 에 로그인한 관리자의 정보를 불러온다.", tags = "CMS Auth API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public ResponseEntity<CmsLoginInfo> info() {
        ResponseEntity<CmsLoginInfo> res;

        res = ResponseEntity.ok(authService.info(SecurityUtils.getLoginUserNo()));

        return res;
    }

    @Operation(summary = "비밀번호 확인", description = "사용자 인증을 위해 비밀번호를 확인한다.", tags = "CMS Auth API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/password/check")
    public ResponseEntity<?> checkPassword(@RequestBody CmsPassword password) {
        authService.checkPassword(password.getPassword());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경한다.", tags = "CMS Auth API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody CmsPassword password) {
        authService.changePassword(password.getPassword());
        return ResponseEntity.ok().build();
    }
}
