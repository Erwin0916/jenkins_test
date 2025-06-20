package com.angkorchat.emoji.cms.domain.studio.auth.api;

import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.StudioSignUp;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistLinkedInfo;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.request.SendAuthenticateCode;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.request.StudioAuthCheck;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.request.StudioLogin;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.response.StudioAuthenticate;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.response.StudioRefreshTokenDto;
import com.angkorchat.emoji.cms.domain.studio.auth.service.StudioAuthService;

import com.angkorchat.emoji.cms.global.config.security.dto.*;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import com.angkorchat.emoji.cms.global.config.security.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Studio Auth API", description = "Studio CMS 로그인 인증을 위한 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("studio")
public class StudioAuthApi {
    private final StudioAuthService studioAuthService;
    private static final Logger log = LoggerFactory.getLogger(StudioAuthApi.class);

    @Operation(summary = "로그인", description = "앙코르 Emoji Studio 로그인을 진행한다. " +
            "\n 로그인 방식은 phone/email, userNo가 0일 경우 Studio 회원가입을 진행한다. ", tags = "Studio Auth API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<StudioTokenDto> studioLogin(HttpServletRequest req, @RequestBody @Valid StudioLogin request) {
        HttpHeaders headers = new HttpHeaders();
        StudioLoginTokenDto res;

        if (request.getAutoLogin()) {
            // 자동 로그인의 경우 로컬 쿠키
            String uuid = UUID.randomUUID().toString();
            log.info("UUID : {}", uuid);
            res = studioAuthService.studioLogin(request, uuid, "");
        } else {
            // 세션 쿠키
            HttpSession session = req.getSession();
            log.info("sessionId : {}", session.getId());
            res = studioAuthService.studioLogin(request, "", session.getId());
        }

        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(res.getRefreshToken(), JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
        ResponseCookie accessCookie = CookieUtils.setAccessCookie(res.getAccessTokenDto().getAccessToken().replace("Bearer ",""), JwtProvider.STUDIO_ACCESS_TOKEN_EXP_TIME);
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.AUTHORIZATION, res.getAccessTokenDto().getAccessToken());

        return ResponseEntity.ok().headers(headers).body(res.getAccessTokenDto());
    }

    // ToDo 호출횟수 제한 필요함.
    @Operation(summary = "Refresh Token 재발급", description = "Access Token 이 만료되었을 때 Refresh Token 으로 AccessToken/RefreshToken 을 재발급 한다.", tags = "Studio Auth API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refresh/token")
    public ResponseEntity<StudioAccessTokenDto> refreshToken(HttpServletRequest req, @Parameter(name = "refreshToken", hidden = true) @CookieValue(value = "refreshToken", required = false) String refreshToken) {

        HttpHeaders headers = new HttpHeaders();
        StudioRefreshTokenDto res = studioAuthService.refreshToken(refreshToken);

        // 세션 쿠키
        if (res.isSessionCookie()) {
            HttpSession session = req.getSession();
            log.info("refresh sessionId : {}", session.getId());
        }

        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(res.getRefreshToken(), JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
        ResponseCookie accessCookie = CookieUtils.setAccessCookie(res.getAccessToken().replace("Bearer ",""), JwtProvider.STUDIO_ACCESS_TOKEN_EXP_TIME);
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.set(HttpHeaders.AUTHORIZATION, res.getAccessToken());

        return ResponseEntity.ok().headers(headers).body(StudioAccessTokenDto.create(res.getAccessToken()));
    }

    @Operation(summary = "request Authenticate by ID(phone/email)", description = "AngkorChat 으로 2차인증을 위한 인증번호를 전송한다.(phone/email)", tags = "Studio Auth API")
    @PostMapping("/auth/code")
    @ResponseStatus(HttpStatus.OK)
    public StudioAuthenticate sendAuthenticateCodeById(@RequestBody SendAuthenticateCode req) {
        return studioAuthService.sendAuthenticateCode(req);
    }

    @Operation(summary = "check Authenticate", description = "인증번호를 입력받아 2차인증을 진행한다.", tags = "Studio Auth API")
    @PostMapping("/auth/check")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudioTokenDto> checkAuthentication(@Valid @RequestBody StudioAuthCheck req) {
        StudioTokenDto res = studioAuthService.checkAuthentication(req);

        return ResponseEntity.ok().body(res);
    }

    @Operation(summary = "회원 가입", description = "회원 가입.", tags = "Studio Auth API")
    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudioTokenDto> studioSignup(HttpServletRequest req, @Valid @RequestBody StudioSignUp request,
                                                       @Parameter(name = "refreshToken", hidden = true) @CookieValue(value = "refreshToken") String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        StudioRefreshTokenDto res = studioAuthService.studioSignup(request, refreshToken);

        // 세션 쿠키
        if (res.isSessionCookie()) {
            HttpSession session = req.getSession();
            log.info("signup refresh sessionId : {}", session.getId());
        }

        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(res.getRefreshToken(), JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
        ResponseCookie accessCookie = CookieUtils.setAccessCookie(res.getAccessToken().replace("Bearer ",""), JwtProvider.STUDIO_ACCESS_TOKEN_EXP_TIME);
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.set(HttpHeaders.AUTHORIZATION, res.getAccessTokenDto().getAccessToken());

        return ResponseEntity.ok().headers(headers).body(res.getAccessTokenDto());
    }

    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃", tags = "Studio Auth API")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> studioLogout(HttpServletRequest req, @Parameter(name = "refreshToken", hidden = true) @CookieValue(value = "refreshToken") String refreshToken,
                                          @Parameter(name = "Authorization", hidden = true) @CookieValue(value = "Authorization") String accessToken) {
        // studioLogout
        HttpHeaders headers = new HttpHeaders();
//        String allowedDomain = domain.replaceAll("https://", "");

        Boolean res = studioAuthService.logout(refreshToken);

        // 세션 쿠키
        if (res) {
            HttpSession session = req.getSession();
            log.info("logout refresh sessionId : {}", session.getId());
        }

        ResponseCookie refreshCookie = CookieUtils.setRefreshCookie(refreshToken, 0);
        ResponseCookie accessCookie = CookieUtils.setAccessCookie(accessToken, 0);
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "AngkorLife 연동 정보", description = "Studio Artist 의 AngkorLife 연동 정보", tags = "Studio Auth API")
    @GetMapping("/linked/info")
    @ResponseStatus(HttpStatus.OK)
    public StudioArtistLinkedInfo studioArtistLinkedInfo() {

        return studioAuthService.studioArtistLinkedInfo();
    }
}
