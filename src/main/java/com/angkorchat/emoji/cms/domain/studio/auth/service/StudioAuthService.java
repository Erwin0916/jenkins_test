package com.angkorchat.emoji.cms.domain.studio.auth.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.*;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.dao.mapper.StudioArtistQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.StudioArtistNameDto;
import com.angkorchat.emoji.cms.domain.studio.term.dto.StudioUserTermList;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.StudioSignUp;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.StudioTermAgreement;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.ArtistInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistLinkedInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistAlreadyRegisteredException;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistNameDuplicateException;
import com.angkorchat.emoji.cms.domain.studio.auth.dao.mapper.StudioAuthQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.request.*;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.response.*;
import com.angkorchat.emoji.cms.domain.studio.term.dao.mapper.StudioTermQueryMapper;
import com.angkorchat.emoji.cms.global.config.redis.service.RedisService;
import com.angkorchat.emoji.cms.global.config.security.dto.*;
import com.angkorchat.emoji.cms.global.config.security.service.Aes256Service;
import com.angkorchat.emoji.cms.global.config.security.service.JwtProvider;
import com.angkorchat.emoji.cms.global.config.security.service.UserAes256Service;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.error.BaseException;
import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import com.angkorchat.emoji.cms.infra.file.service.S3Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudioAuthService {
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final UserAes256Service  userAes256Service;
    private final Aes256Service aes256Service;
    private final S3Service s3Service;

    private final StudioAuthQueryMapper studioAuthQueryMapper;
    private final StudioArtistQueryMapper studioArtistQueryMapper;
    private final StudioTermQueryMapper studioTermQueryMapper;

    @Value("${authApi.url}")
    private String authUrl;
    @Value("${emoji.studio.appKey}")
    private String studioAppKey;
    @Value("${emoji.studio.secretKey}")
    private String studioSecretKey;
    @Value("${authApi.chat.appKey}")
    private String chatAppKey;
    @Value("${authApi.chat.secretKey}")
    private String chatSecretKey;
    @Value("${chatCmsApi.url}")
    private String chatCmsUrl;

    /**
     * 로그인
     */
    public StudioLoginTokenDto studioLogin(StudioLogin request, String uuid, String sessionId) {
        // 외부 인증서버에 접속하여 angkorChat userAngkorId 존재여부 확인
        String url;
        Map<String, String> headerMap = new HashMap<>();
        Map<String, String> requestMap = new HashMap<>();

        // ---------------------- 챗 유저 로그인(유저인지 확인) ----------------------------
        url = authUrl + "/oauth/v2/login";

        headerMap.put("AppKey", chatAppKey);
        headerMap.put("Authorization", chatSecretKey);

        requestMap.put("grantType", request.getGrantType());
        requestMap.put("id", request.getId());
        requestMap.put("password", request.getPassword());

        Map<String, Object> chatExceptionMap = new HashMap<>();
        chatExceptionMap.put("status", HttpStatus.BAD_REQUEST);
        chatExceptionMap.put("code", "400441");
        chatExceptionMap.put("exception", PasswordWrongException.class);

        CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, chatExceptionMap);

        // 유저정보 조회
        UserInfo userInfo = this.angkorLifeUserInfo(request.getId());

        // ---------------------- 스튜디오 유저 로그인 ----------------------------
        headerMap.put("AppKey", studioAppKey);
        headerMap.put("Authorization", studioSecretKey);

        Map<String, Object> exceptionMap = new HashMap<>();
        exceptionMap.put("status", HttpStatus.BAD_REQUEST);
        exceptionMap.put("code", "400501");
        exceptionMap.put("exception", UserNotFoundException.class);
        try {
            CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, exceptionMap);
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof UserNotFoundException) {
                // 인증서버 Studio 미가입자
                StudioLoginDto artistInfo = new StudioLoginDto();
                artistInfo.setId(0);
                artistInfo.setAngkorId(userInfo.getAngkorId());
                try {
                    artistInfo.setName(userAes256Service.decrypt(userInfo.getScreenName()));
                    artistInfo.setPhoneNumber(userAes256Service.decrypt(userInfo.getPhoneNumber()));
                    artistInfo.setEmail(userAes256Service.decrypt(userInfo.getEmail()));
                } catch (Exception ex) {
                    throw new BaseException();
                }

                String refreshToken = jwtProvider.createStudioRefreshToken(userInfo.getAngkorId(), uuid, sessionId, 0, false);
                StudioTokenDto accessToken = jwtProvider.createStudioAccessTokenDto(artistInfo, false);

                StudioLoginTokenDto res = new StudioLoginTokenDto();

                res.setAccessTokenDto(accessToken);
                res.setRefreshToken(refreshToken);
                res.setArtist(false);
                // 이거 레디스에 저장한다.
                if ("".equals(sessionId)) {
                    redisService.saveRefreshToken(String.format("artist:%s:uuid:%s:refreshToken", userInfo.getAngkorId(), uuid), refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
                } else {
                    redisService.saveRefreshToken(String.format("artist:%s:session:%s:refreshToken", userInfo.getAngkorId(), sessionId), refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
                }

                return res;
            } else {
                throw e; // 또는 다른 처리
            }
        }

        String linkedName;
        String linkedPhoneNumber;
        String linkedEmail;

        try {
            linkedName = userAes256Service.decrypt(userInfo.getScreenName());
            linkedPhoneNumber = userAes256Service.decrypt(userInfo.getPhoneNumber());
            linkedEmail = userAes256Service.decrypt(userInfo.getEmail());
        } catch (Exception ex) {
            throw new BaseException();
        }

        StudioLoginDto artist = studioAuthQueryMapper.studioArtistLogin(userInfo.getAngkorId());
        // Studio 회원가입 절차 미진행자
        if(artist == null) {
            StudioLoginDto artistInfo = new StudioLoginDto();
            artistInfo.setId(0);
            artistInfo.setAngkorId(userInfo.getAngkorId());
            try {
                artistInfo.setName(linkedName);
                artistInfo.setPhoneNumber(linkedPhoneNumber);
                artistInfo.setEmail(linkedEmail);
            } catch (Exception ex) {
                throw new BaseException();
            }

            String refreshToken = jwtProvider.createStudioRefreshToken(userInfo.getAngkorId(), uuid, sessionId, 0, false);
            StudioTokenDto accessToken = jwtProvider.createStudioAccessTokenDto(artistInfo, false);

            StudioLoginTokenDto res = new StudioLoginTokenDto();

            res.setAccessTokenDto(accessToken);
            res.setRefreshToken(refreshToken);
            res.setArtist(false);
            // 이거 레디스에 저장한다.
            if ("".equals(sessionId)) {
                redisService.saveRefreshToken(String.format("artist:%s:uuid:%s:refreshToken", userInfo.getAngkorId(), uuid), refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
            } else {
                redisService.saveRefreshToken(String.format("artist:%s:session:%s:refreshToken", userInfo.getAngkorId(), sessionId), refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
            }

            return res;
        }

        artist.setName(linkedName);
        artist.setPhoneNumber(linkedPhoneNumber);
        artist.setEmail(linkedEmail);

        String refreshToken = jwtProvider.createStudioRefreshToken(artist.getAngkorId(), uuid, sessionId, artist.getId(), true);
        StudioTokenDto accessToken = jwtProvider.createStudioAccessTokenDto(artist, true);

        StudioLoginTokenDto res = new StudioLoginTokenDto();

        res.setAccessTokenDto(accessToken);
        res.setRefreshToken(refreshToken);
        res.setArtist(true);

        // 이거 레디스에 저장한다.
        if ("".equals(sessionId)) { // 퍼시스턴스 쿠키
            redisService.saveRefreshToken(String.format("artist:%s:uuid:%s:refreshToken", artist.getAngkorId(), uuid), refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
        } else { // 세션 쿠키
            redisService.saveRefreshToken(String.format("artist:%s:session:%s:refreshToken", artist.getAngkorId(), sessionId), refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);
        }

        return res;
    }

    public StudioRefreshTokenDto refreshToken(String refreshToken) {
        Claims claims = jwtProvider.parseStudioRefreshClaims("Bearer " + refreshToken);

        String angkorId = claims.getSubject();
        String sessionId = claims.get("sessionId", String.class);
        String uuid = claims.get("uuid", String.class);
        Integer artistId = claims.get("artistId", Integer.class);
        boolean sessionCookie = uuid.isEmpty();

        String key;
        String accessToken;

        if (claims.get("isSecondAuthenticated", Boolean.class)) {
            refreshToken = jwtProvider.createStudioRefreshToken(angkorId, uuid, sessionId, artistId, true);
            accessToken = jwtProvider.createStudioToken(angkorId, artistId, true);
        } else {
            refreshToken = jwtProvider.createStudioRefreshToken(angkorId, uuid, sessionId, artistId, false);
            accessToken = jwtProvider.createStudioToken(angkorId, artistId, false);
        }

        if (sessionCookie) {
            key = String.format("artist:%s:session:%s:refreshToken", angkorId, sessionId);
        } else {
            key = String.format("artist:%s:uuid:%s:refreshToken", angkorId, uuid);
        }

        redisService.updateRefreshToken(key, refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);

        // 레디스에서 해당 키에 있는 값 업데이트
        StudioRefreshTokenDto res = new StudioRefreshTokenDto();

        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);
        res.setSessionCookie(sessionCookie);

        return res;
    }

    /**
     * 2차 인증번호 전송
     */
    public StudioAuthenticate sendAuthenticateCode(SendAuthenticateCode req) {
        // 인증번호 전송
        String url;
        Map<String, String> headerMap = new HashMap<>();
        Map<String, String> requestMap = new HashMap<>();

        url = authUrl + "/easy-join/v2/join";

        headerMap.put("AppKey", studioAppKey);
        headerMap.put("Authorization", studioSecretKey);
        requestMap.put("grantType", req.getGrantType());
        requestMap.put("userConsent", "127");
        requestMap.put("id", req.getId());

        ResponseEntity<String> response = CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, null);
        JsonNode jsonNode = CommonUtils.responseBodyToJsonNode(response.getBody());

        // 2차 인증 시도 시간 저장
        studioAuthQueryMapper.insertStudioAuthKey(req.getId(), jsonNode.get("authKey").asText());

        return new StudioAuthenticate(req.getId(), jsonNode.get("tokenType").asText(), jsonNode.get("authKey").asText());
    }

    /**
     * 2차 인증
     */
    public StudioTokenDto checkAuthentication(StudioAuthCheck req) {
        // 인증 시간 체크
        String authKey = req.getAuthKey().replaceAll("bearer ", "");
        StudioAuthKey studioAuthKey = studioAuthQueryMapper.checkStudioAuthKey(req.getId(), authKey);

        if (studioAuthKey == null) {
            throw new UserNotFoundException();
        } else if (LocalDateTime.now().isAfter(studioAuthKey.getExpireTm())) {
            throw new ExpiredSecondAuthException();
        }

        // 인증번호 확인
        String url;
        Map<String, String> headerMap = new HashMap<>();
        Map<String, String> requestMap = new HashMap<>();

        // 프로필 별로 분기
        url = authUrl + "/easy-join/v2/verify";

        headerMap.put("AppKey", studioAppKey);
        headerMap.put("Authorization", studioSecretKey);
        headerMap.put("AuthKey", req.getAuthKey());
        requestMap.put("grantType", req.getGrantType());
        requestMap.put("authNumber", req.getAuthNumber());
        requestMap.put("userid", req.getId());

        Map<String, Object> exceptionMap = new HashMap<>();
        exceptionMap.put("status", HttpStatus.BAD_REQUEST);
        exceptionMap.put("code", "400603");
        exceptionMap.put("exception", AuthNumberMissMatchException.class);

        CommonUtils.callApi(url, HttpMethod.POST, headerMap, requestMap, exceptionMap);

        String angkorId = SecurityUtils.getStudioLoginAngkorId();

        if(angkorId == null) {
            throw new AuthenticationFailException();
        }

        UserInfo userInfo = this.angkorLifeUserInfo(req.getId());

        StudioLoginDto artistInfo = new StudioLoginDto();
        artistInfo.setId(0);
        artistInfo.setAngkorId(userInfo.getAngkorId());
        try {
            artistInfo.setName(userAes256Service.decrypt(userInfo.getScreenName()));
            artistInfo.setPhoneNumber(userAes256Service.decrypt(userInfo.getPhoneNumber()));
            artistInfo.setEmail(userAes256Service.decrypt(userInfo.getEmail()));
        } catch (Exception ex) {
            throw new BaseException();
        }

        // 이거 자동로그인시에 2차인증 받은 후 상태 기록위해서는 redis 갱신 해주어야함.
        // 그러나 현재 인증을 받더라도 가입을 하지 않은 유저는 재로그인을 하는 정책이기 때문에 불필요.
        return jwtProvider.createStudioAccessTokenDto(artistInfo, true);
    }

    public UserInfo angkorLifeUserInfo(String id) {
        String url = chatCmsUrl + "/users/info/user/" + id;

        ResponseEntity<String> response = CommonUtils.callApi(url, HttpMethod.GET, null, null, null);

        JsonNode userInfoList = CommonUtils.responseBodyToJsonNode(response.getBody());

        if (userInfoList == null || userInfoList.isEmpty()) {
            throw new UserNotFoundException();
        }
        List<UserInfo> userList;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userList = objectMapper.readValue(
                    userInfoList.toString(),
                    new TypeReference<>() {
                    }
            );
        } catch (Exception ex) {
            throw new BaseException();
        }

        UserInfo userInfo = userList.stream()
                .filter(Objects::nonNull) // null 객체 제거
                .filter(user -> {
                    String status = user.getStatus();
                    return status != null && (status.equals("normal") || status.equals("reported"));
                })
                .findFirst()  // 첫 번째 결과만 추출
                .orElse(null); // 없을 경우 null 반환


        if (userInfo == null) {
            throw new UserNotFoundException();
        }

        return userInfo;
    }


    @Transactional
    public StudioRefreshTokenDto studioSignup(StudioSignUp req, String refreshToken) {
        String angkorId = SecurityUtils.getStudioLoginAngkorId();

        if (angkorId == null) {
            throw new AuthenticationFailException();
        }

        ArtistInfo artistInfo = studioArtistQueryMapper.getEmojiArtistByAngkorId(angkorId);

        if (artistInfo != null) {
            throw new ArtistAlreadyRegisteredException();
        }

        if (!req.getPhoneNumber().startsWith(req.getPhoneCode())) {
            throw new InvalidInputDataFormatException("phoneCode missMatch");
        }

        String phone = req.getPhoneNumber().substring(req.getPhoneCode().length());
        if (!phone.startsWith("0")) {
            phone = "0" + phone;
        }
        req.setPhone(phone);
        req.setAngkorId(angkorId);

        StudioArtistNameDto nameCheck = studioArtistQueryMapper.checkArtistNameDuplicate(req.getArtistName(), req.getArtistNameKm());

        if (nameCheck != null) {
            if (nameCheck.getNameEn().equals(req.getArtistName())) {
                throw new ArtistNameDuplicateException("artistNameEn");
            }
            if (nameCheck.getNameKm().equals(req.getArtistNameKm())) {
                throw new ArtistNameDuplicateException("artistNameKm");
            }
        }

        // ARTIST 회원 가입
        studioArtistQueryMapper.artistSignup(req);

        studioArtistQueryMapper.setArtistAttachFile(req.getId(), req.getAttachFileUrl());

        // ARTIST 로그
        studioArtistQueryMapper.insertArtistLog(req.getId());
        List<Integer> ids = req.getTermAgreement().stream()
                .map(StudioTermAgreement::getTermId)
                .toList();

        // 약관 버전, 타입 정보 조회
        List<StudioUserTermList> termList = studioTermQueryMapper.getStudioTermList(ids);

        Map<Integer, String> idToAgreeYnMap = req.getTermAgreement().stream()
                .collect(Collectors.toMap(
                        StudioTermAgreement::getTermId,
                        term -> term.getAgreeYn() ? "Y" : "N"
                ));

        for (StudioUserTermList term : termList) {
            String agreeYn = idToAgreeYnMap.get(term.getTermId());
            term.setAgreeYn(agreeYn);
        }

        // 약관 동의
        studioArtistQueryMapper.artistTermAgreement(angkorId, termList);

        List<Integer> agreeIds = termList.stream()
                .map(StudioUserTermList::getAgreeId)
                .toList();

        // 약관 동의 로그
        studioArtistQueryMapper.artistTermAgreementLog(agreeIds);

        /// ////////////////////////////////////////////////////
        Claims claims = jwtProvider.parseStudioRefreshClaims("Bearer " + refreshToken);

        angkorId = claims.getSubject();
        String sessionId = claims.get("sessionId", String.class);
        String uuid = claims.get("uuid", String.class);
        boolean sessionCookie = uuid.isEmpty();

        String key;
        String accessToken;

        // 가입 성공시 2차인증 : true, 생성된 userNo 로 리프레시 토큰 생성
        refreshToken = jwtProvider.createStudioRefreshToken(angkorId, uuid, sessionId, req.getId(), true);

        UserInfo userInfo = this.cmsUserInfo(List.of(angkorId));

        StudioLoginDto artist = new StudioLoginDto();
        artist.setId(req.getId());
        artist.setAngkorId(angkorId);
        try {
            artist.setName(userAes256Service.decrypt(userInfo.getScreenName()));
            artist.setPhoneNumber(userAes256Service.decrypt(userInfo.getPhoneNumber()));
            artist.setEmail(userAes256Service.decrypt(userInfo.getEmail()));
        } catch (Exception ex) {
            throw new BaseException();
        }

        StudioTokenDto accessTokenDto = jwtProvider.createStudioAccessTokenDto(artist, true);
        if (sessionCookie) {
            key = String.format("artist:%s:session:%s:refreshToken", angkorId, sessionId);
        } else {
            key = String.format("artist:%s:uuid:%s:refreshToken", angkorId, uuid);
        }

        redisService.updateRefreshToken(key, refreshToken, JwtProvider.STUDIO_REFRESH_TOKEN_EXP_TIME);

        // 레디스에서 해당 키에 있는 값 업데이트

        StudioRefreshTokenDto res = new StudioRefreshTokenDto();

        res.setAccessTokenDto(accessTokenDto);
        res.setRefreshToken(refreshToken);
        res.setSessionCookie(sessionCookie);

        return res;
    }

    public Boolean logout(String refreshToken) {
        Claims claims = jwtProvider.parseStudioRefreshClaims("Bearer " + refreshToken);

        String angkorId = claims.getSubject();
        String sessionId = claims.get("sessionId", String.class);
        String uuid = claims.get("uuid", String.class);
        boolean sessionCookie = uuid.isEmpty();

        String key;

        if (sessionCookie) {
            key = String.format("artist:%s:session:%s:refreshToken", angkorId, sessionId);
        } else {
            key = String.format("artist:%s:uuid:%s:refreshToken", angkorId, uuid);
        }

        redisService.deleteRefreshToken(key);

        return sessionCookie;
    }

    public StudioArtistLinkedInfo studioArtistLinkedInfo() {
        String angkorId = SecurityUtils.getStudioLoginAngkorId();
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(angkorId == null) {
            throw new AuthenticationFailException();
        }
        UserInfo userInfo = this.cmsUserInfo(List.of(angkorId));

        StudioArtistLinkedInfo linkedInfo = new StudioArtistLinkedInfo();
        linkedInfo.setId(id);
        try {
            linkedInfo.setEmail(userAes256Service.decrypt(userInfo.getEmail()));
            linkedInfo.setUserName(userAes256Service.decrypt(userInfo.getScreenName()));
            linkedInfo.setPhoneNumber(userAes256Service.decrypt(userInfo.getPhoneNumber()));
        } catch (Exception ex) {
            throw new BaseException();
        }

        linkedInfo.setProfileImage(userInfo.getProfileImage());
        linkedInfo.setAngkorId(angkorId);

        return linkedInfo;
    }

    private UserInfo cmsUserInfo(List<String> angkorIdList) {
        HashMap<String, List<String>> requestMap = new HashMap<>();
        requestMap.put("angkorIdList", angkorIdList);
        String url = chatCmsUrl + "/users/info/user/";
        ResponseEntity<String> response = CommonUtils.callApi(url, HttpMethod.POST, null, requestMap, null);

        JsonNode userInfoList = CommonUtils.responseBodyToJsonNode(response.getBody());

        if (userInfoList == null || userInfoList.isEmpty()) {
            throw new UserNotFoundException();
        }
        List<UserInfo> userList;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userList = objectMapper.readValue(
                    userInfoList.toString(),
                    new TypeReference<>() {
                    }
            );
        } catch (Exception ex) {
            throw new BaseException();
        }

        UserInfo userInfo = userList.stream()
                .filter(Objects::nonNull) // null 객체 제거
                .filter(user -> {
                    String status = user.getStatus();
                    return status != null && (status.equals("normal") || status.equals("reported"));
                })
                .findFirst()  // 첫 번째 결과만 추출
                .orElse(null); // 없을 경우 null 반환


        if (userInfo == null) {
            throw new UserNotFoundException();
        }

        return userInfo;
    }
}
