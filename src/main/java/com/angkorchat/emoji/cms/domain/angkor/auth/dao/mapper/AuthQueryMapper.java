package com.angkorchat.emoji.cms.domain.angkor.auth.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsAuthKey;
import com.angkorchat.emoji.cms.domain.angkor.auth.dto.response.CmsLoginInfo;
import com.angkorchat.emoji.cms.global.config.security.dto.LoginDto;
import org.apache.ibatis.annotations.Param;

public interface AuthQueryMapper {
    LoginDto adminLogin(String loginId);

    CmsLoginInfo loginInfo(Integer id);

    void increaseTryCnt(@Param("loginId") String loginId,
                        @Param("tryCnt") Byte tryCnt);

    void integrationAngkorUserId(@Param("loginId") String loginId,
                                 @Param("userAngkorId") String userAngkorId,
                                 @Param("angkorId") String angkorId);

    void changePassword(@Param("loginId") String loginId,
                        @Param("password") String password);

    void insertAuthKey(@Param("angkorId") String angkorId,
                       @Param("authKey") String authKey);

    CmsAuthKey checkAuthKey(@Param("angkorId") String angkorId);

    void updateRefreshToken(@Param("id") Integer id,
                            @Param("refreshToken") String refreshToken);

    int checkRefreshToken(@Param("id") Integer id,
                           @Param("refreshToken") String refreshToken);
}
