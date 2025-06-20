package com.angkorchat.emoji.cms.domain.angkor.auth.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.dto.request.CmsLogin;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsAuthenticate;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsLoginInfo;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsTokenDto;
import com.angkorchat.emoji.cms.domain.angkor.auth.exception.*;
import com.angkorchat.emoji.cms.domain.angkor.auth.dao.mapper.AuthQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserInfo;
import com.angkorchat.emoji.cms.global.config.security.dto.LoginDto;
import com.angkorchat.emoji.cms.global.config.security.dto.LoginTokenDto;
import com.angkorchat.emoji.cms.global.config.security.dto.TokenDto;
import com.angkorchat.emoji.cms.global.config.security.exception.RefreshTokenException;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import com.angkorchat.emoji.cms.global.config.security.service.UserAes256Service;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.error.BaseException;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtProvider jwtProvider;
    private final AuthQueryMapper authQueryMapper;
    private final UserAes256Service userAes256Service;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${chatCmsApi.url}")
    private String chatCmsApiUrl;
    @Value("${authApi.url}")
    private String authUrl;
    @Value("${authApi.cms.appKey}")
    private String appKey;
    @Value("${authApi.cms.secretKey}")
    private String secretKey;
    @Value("${emoji.cms.appKey}")
    private String emojiAppKey;
    @Value("${emoji.cms.secretKey}")
    private String emojiSecretKey;

    /**
     * 로그인
     */
    public LoginTokenDto login(CmsLogin request) {
        LoginDto admin = authQueryMapper.adminLogin(request.getId());

        // 비밀번호 오류 횟수 확인
        if (admin.getPassword().getWrongCount() >= admin.getPassword().getMaxWrongCount()) {
            throw new PasswordWrongCountExceededException();
        }

        // 비밀번호 확인
        if (!request.getPassword().equals(admin.getPassword().getPw())) {
            if (admin.getPassword().increaseWrongPasswordCount()) {
                // 비밀번호 오류시 횟수 추가
                authQueryMapper.increaseTryCnt(request.getId(), admin.getPassword().getWrongCount());

                Map<String, Object> data = new HashMap<>();

                data.put("passWrongCnt", admin.getPassword().getWrongCount());

                throw new UserLoginFailedException(data);
            } else {
                // 비밀번호 오류 횟수 5회 초과시
                throw new PasswordWrongCountExceededException();
            }
        }

        // 로그인 성공시 비밀번호 오류 횟수가 1회 이상일때 비밀번호 오류 횟수 초기화
        if (admin.getPassword().getWrongCount() != 0) {
            admin.resetTryCnt(admin.getPassword().getPw());

            authQueryMapper.increaseTryCnt(request.getId(), Byte.parseByte("0"));
        }

        if (!admin.getUserAngkorId().isEmpty()) {
            try {
                admin.setUserAngkorId(userAes256Service.decrypt(admin.getUserAngkorId()));
            } catch (Exception e) {
                log.error("login userAngkorId Decrypt Exception");
                throw new BaseException();
            }
        }

        String refreshToken = jwtProvider.createRefreshToken(admin.getLoginId(), false);
        TokenDto accessToken = jwtProvider.createAccessTokenDto(admin, false);
        LoginTokenDto res = new LoginTokenDto();

        res.setAccessTokenDto(accessToken);
        res.setRefreshToken(refreshToken);

        authQueryMapper.updateRefreshToken(admin.getId(), refreshToken);
        // 1차 인증 토큰 발행(2차 인증 필요)
        return res;
    }

    public CmsTokenDto refreshToken(String refreshToken) {
        String accessToken;
        LoginDto admin;

        Claims claims = jwtProvider.parseCmsRefreshClaims("Bearer " + refreshToken);

        String loginId = claims.getSubject();
        admin = authQueryMapper.adminLogin(loginId);

        if (admin == null) {
            throw new InvalidAdminUserException();
        }

        int cnt = authQueryMapper.checkRefreshToken(admin.getId(), refreshToken);
        if (cnt == 0) {
            throw new RefreshTokenException();
        }
        if (claims.get("isSecondAuthenticated", Boolean.class)) {
            refreshToken = jwtProvider.createRefreshToken(loginId, true);
            accessToken = jwtProvider.createToken(loginId, true);
        } else {
            refreshToken = jwtProvider.createRefreshToken(loginId, false);
            accessToken = jwtProvider.createToken(loginId, false);
        }

        CmsTokenDto res = new CmsTokenDto();

        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);

        authQueryMapper.updateRefreshToken(admin.getId(), refreshToken);

        return res;
    }

    /**
     * 2차 인증을 위한 AngkorChat Id 확인
     */
    public void checkAngkorId(String userAngkorId) {
        // 외부 인증서버에 접속하여 angkorChat userAngkorId 존재여부 확인
        String url;
        Map<String, String> headerMap = new HashMap<>();
        Map<String, String> requestMap = new HashMap<>();

        // 프로필 별로 분기
        url = authUrl + "/easy-join/v2/find";

        headerMap.put("AppKey", appKey);
        headerMap.put("Authorization", secretKey);
        requestMap.put("grantType", "angkor");
        requestMap.put("id", userAngkorId);

        Map<String, Object> exceptionMap = new HashMap<>();
        exceptionMap.put("status", HttpStatus.BAD_REQUEST);
        exceptionMap.put("code", "400412");
        exceptionMap.put("exception", UserNotFoundException.class);

        CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, exceptionMap);

    }

    /**
     * 2차 인증번호 전송
     */
    public CmsAuthenticate sendAuthenticateCode(String id) {
        // 인증번호 전송
//        String url;
//        Map<String, String> headerMap = new HashMap<>();
//        Map<String, String> requestMap = new HashMap<>();
//
//        url = authUrl + "/easy-join/v2/join";
//
//        headerMap.put("AppKey", emojiAppKey);
//        headerMap.put("Authorization", emojiSecretKey);
//        requestMap.put("grantType", "angkor");
//        requestMap.put("userConsent", "127");
//        //userAngkorId
//        requestMap.put("id", id);
//
//        ResponseEntity<String> response = CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, null);
//        JsonNode jsonNode = CommonUtils.responseBodyToJsonNode(response.getBody());
//
//        // 2차 인증 시도 시간 저장
//        authQueryMapper.insertAuthKey(id, jsonNode.get("authKey").asText());
//
//        return new CmsAuthenticate(id, jsonNode.get("tokenType").asText(), jsonNode.get("authKey").asText());


        return new CmsAuthenticate(id, "bearer", "abc");
    }

    /**
     * 2차 인증
     */
    public LoginTokenDto checkAuthentication(String authKey, String authNumber, String userAngkorId) {
//        // 인증 시간 체크
//        CmsAuthKey cmsAuthKey = authQueryMapper.checkAuthKey(userAngkorId);
//
//        if (cmsAuthKey == null) {
//            throw new UserNotFoundException();
//        } else if (!authKey.contains(cmsAuthKey.getAuthKey()) || cmsAuthKey.getExpireTm().isBefore(LocalDateTime.now())) {
//            throw new ExpiredSecondAuthException();
//        }
        LoginDto admin = authQueryMapper.adminLogin(SecurityUtils.getLoginUserLoginId());
//        // 인증번호 확인
        String url;
//        Map<String, String> headerMap = new HashMap<>();
//        Map<String, String> requestMap = new HashMap<>();
//
//        // 프로필 별로 분기
//        url = authUrl + "/easy-join/v2/verify";
//
//        headerMap.put("AppKey", emojiAppKey);
//        headerMap.put("Authorization", emojiSecretKey);
//        headerMap.put("AuthKey", authKey);
//        requestMap.put("grantType", "angkor");
//        requestMap.put("authNumber", authNumber);
//        requestMap.put("userid", userAngkorId);
//
//        Map<String, Object> exceptionMap = new HashMap<>();
//        exceptionMap.put("status", HttpStatus.BAD_REQUEST);
//        exceptionMap.put("code", null);
//        exceptionMap.put("exception", AuthenticationFailException.class);
//
//        CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, exceptionMap);

//        admin = authQueryMapper.adminLogin(SecurityUtils.getLoginUserLoginId());

        // AngkorChat 아이디 연동여부 확인
        if (admin.getUserAngkorId().isEmpty()) {
            url = chatCmsApiUrl + "/users/info/user/" + userAngkorId;
            ResponseEntity<String> response = CommonUtils.callApi(url, HttpMethod.GET, null, null, null);

            log.info(CommonUtils.logRequest(response));

            UserInfo res;
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<UserInfo> userList = objectMapper.readValue(
                        response.getBody(),
                        new TypeReference<>() {}
                );

                if(userList == null || userList.isEmpty()) {
                    throw new UserNotFoundException();
                }
                res = userList.get(0);
            } catch (Exception e) {
                log.error("UserInfo Json Parsing Exception");
                throw new RuntimeException();
            }

            authQueryMapper.integrationAngkorUserId(admin.getLoginId(), res.getUserAngkorId(), res.getAngkorId());
        }
        // 디코딩된 값으로 토큰 생성
        admin.setUserAngkorId(userAngkorId);


        String refreshToken = jwtProvider.createRefreshToken(admin.getLoginId(), true);
        TokenDto accessToken = jwtProvider.createAccessTokenDto(admin, true);
        LoginTokenDto res = new LoginTokenDto();

        res.setAccessTokenDto(accessToken);
        res.setRefreshToken(refreshToken);

        authQueryMapper.updateRefreshToken(admin.getId(), refreshToken);
        // 2차 인증 토큰 발행
        return res;
    }


    public CmsLoginInfo info(Integer id) {
        CmsLoginInfo info = authQueryMapper.loginInfo(id);

        try {
            info.setUserAngkorId(userAes256Service.decrypt(info.getUserAngkorId()));
        } catch (Exception e) {
            log.error("userInfo Encrypt Exception");
            throw new BaseException();
        }

        return info;
    }

    public void checkPassword(String password) {
        LoginDto admin = authQueryMapper.adminLogin(SecurityUtils.getLoginUserLoginId());

        if (!password.equals(admin.getPassword().getPw())) {
            throw new UserLoginFailedException();
        }
    }

    public void changePassword(String password) {
        LoginDto admin = authQueryMapper.adminLogin(SecurityUtils.getLoginUserLoginId());

        authQueryMapper.changePassword(admin.getLoginId(), password);
    }
}
