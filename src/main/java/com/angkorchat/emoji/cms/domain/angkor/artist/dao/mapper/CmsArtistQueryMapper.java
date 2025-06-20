package com.angkorchat.emoji.cms.domain.angkor.artist.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.artist.dto.request.RegisterArtistAccount;
import com.angkorchat.emoji.cms.domain.angkor.artist.dto.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CmsArtistQueryMapper {
    List<ArtistListDto> emojiArtistList(@Param("pageable") Pageable pageable,
                                        @Param("status") String status,
                                        @Param("searchKeyword") String searchKeyword);

    long emojiArtistListCount(@Param("status") String status,
                              @Param("searchKeyword") String searchKeyword);

    ArtistDetail emojiArtistDetail(Integer id);

    List<ArtistEmojiListDto> artistEmojiList(@Param("pageable") Pageable pageable,
                                             @Param("artistId") Integer artistId,
                                             @Param("status") String status,
                                             @Param("searchKeyword") String searchKeyword);

    long artistEmojiListCount(@Param("artistId") Integer artistId,
                              @Param("status") String status,
                              @Param("searchKeyword") String searchKeyword);


    ArtistAccountInfo cmsArtistAccountInfo(@Param("id") Integer id,
                                           @Param("statDate") String statDate);

    List<ArtistSettlementRequestList> cmsArtistSettlementRequestList(@Param("pageable") Pageable pageable,
                                                                     @Param("artistId") Integer artistId);

    long cmsArtistSettlementRequestListCount(Integer artistId);

    int checkArtistAccountExist(Integer artistId);

    void updateArtistBankInfo(RegisterArtistAccount req);

    void registerArtistAccount(Integer artistId);
}


