package com.angkorchat.emoji.cms.domain.studio.auth.dao.mapper;


import com.angkorchat.emoji.cms.domain.studio.auth.dto.response.StudioAuthKey;
import com.angkorchat.emoji.cms.domain.studio.auth.dto.response.StudioLoginInfo;
import com.angkorchat.emoji.cms.global.config.security.dto.StudioLoginDto;
import org.apache.ibatis.annotations.Param;


public interface StudioAuthQueryMapper {
    StudioLoginDto studioArtistLogin(String loginId);

    StudioLoginInfo studioLoginInfo(String angkorId);

    void insertStudioAuthKey(@Param("id") String id,
                             @Param("authKey") String authKey);

    StudioAuthKey checkStudioAuthKey(@Param("id") String id,
                                     @Param("authKey") String authKey);
}
