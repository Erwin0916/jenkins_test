package com.angkorchat.emoji.cms.domain.studio.artist.dao.mapper;

import com.angkorchat.emoji.cms.domain.studio.artist.dto.StudioArtistNameDto;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.ModifyStudioArtist;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.ArtistInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistBankInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistDetail;
import com.angkorchat.emoji.cms.domain.studio.term.dto.StudioUserTermList;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.StudioSignUp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudioArtistQueryMapper {

    ArtistInfo getEmojiArtistByAngkorId(@Param("angkorId") String angkorId);

    StudioArtistDetail studioArtistDetail(Integer id);

    void modifyStudioArtistInfo(ModifyStudioArtist req);

    void insertArtistLog(Integer id);

    StudioArtistNameDto checkArtistNameDuplicate(@Param("name") String name,
                                                 @Param("nameKm") String nameKm);

    void artistSignup(StudioSignUp req);

    void setArtistAttachFile(@Param("id") Integer id,
                             @Param("url") String url);

    void artistTermAgreement(@Param("angkorId") String angkorId,
                             @Param("req") List<StudioUserTermList> req);

    void artistTermAgreementLog(@Param("ids") List<Integer> ids);

    StudioArtistBankInfo studioArtistBankInfo(Integer id);
}
