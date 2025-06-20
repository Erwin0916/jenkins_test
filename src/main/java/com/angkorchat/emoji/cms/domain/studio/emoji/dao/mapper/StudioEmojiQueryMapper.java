package com.angkorchat.emoji.cms.domain.studio.emoji.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiFileDto;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.request.ModifyStudioEmoji;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.request.ModifyStudioEmojiStatus;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.request.RegisterStudioEmoji;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.request.StudioEmojiRequestDto;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.response.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudioEmojiQueryMapper {
    EmojiStatusCount emojiStatusCount(Integer artistId);

    List<StudioArtistEmojiList> studioArtistEmojiList(@Param("pageable") Pageable pageable,
                                                      @Param("proposal") Boolean proposal,
                                                      @Param("artistId") Integer artistId,
                                                      @Param("status") String status,
                                                      @Param("imageType") String imageType);

    long studioArtistEmojiListCount(@Param("proposal") Boolean proposal,
                                    @Param("artistId") Integer artistId,
                                    @Param("status") String status,
                                    @Param("imageType") String imageType);

    StudioArtistEmojiDetail studioArtistEmojiDetail(@Param("id") Integer id,
                                                    @Param("artistId") Integer artistId);

    String checkStudioEmojiStatus(Integer id);

    void registerArtistStudioEmojiMaster(RegisterStudioEmoji req);

    void registerArtistStudioEmojiMasterFile(@Param("id") Integer id,
                                             @Param("mainEmojiUrl") String mainEmojiUrl,
                                             @Param("mainMovingEmojiUrl") String mainMovingEmojiUrl,
                                             @Param("attachmentFileUrl") String attachmentFileUrl);

    void registerArtistStudioEmojiShortcut(@Param("id") Integer id,
                                           @Param("tabEmojiUrl") String tabEmojiUrl);

    void registerArtistStudioEmojiList(@Param("id") Integer id,
                                       @Param("fileList") List<EmojiFileDto> fileList);


    Integer getTagNameFilterList(String tagName);
    // 신규 태그 저장
    void registerStudioHashTags(@Param("tags") List<StudioTagDto> tags);

    // 해당 태그들 태그 id 가져오기
    List<StudioTagDto> studioHashTagInfoListByTags(@Param("tags") List<StudioTagDto> tags);

    // 아티스트 태그에 저장
    void registerArtistStudioHashTags(@Param("emojiId") Integer emojiId,
                                      @Param("tags") List<StudioTagDto> tags);

    void registerArtistStudioRelatedSites(@Param("emojiId") Integer emojiId,
                                          @Param("sites") List<String> sites);


    void modifyStudioEmoji(ModifyStudioEmoji req);

    void modifyArtistStudioEmojiShortcut(@Param("emojiId") Integer emojiId,
                                         @Param("tabEmojiUrl") String tabEmojiUrl);

    void modifyArtistStudioEmojiList(@Param("emojiId") Integer emojiId,
                                     @Param("req") List<StudioEmojiRequestDto> req);

    void deleteArtistEmojiHashTags(Integer emojiId);

    void  deleteArtistStudioRelatedSites(Integer emojiId);

    Integer getStudioEmojiId(Integer artistEmojiId);

    void modifyStudioEmojiStatus(ModifyStudioEmojiStatus req);

    void registerArtistStudioEmojiMasterLog(Integer id);

    void registerArtistStudioEmojiShortcutLog(Integer id);

    void registerArtistStudioEmojiListLog(Integer id);

    void registerStudioEmojiMasterLog(Integer emojiId);

    void registerStudioEmojiShortcutLog(Integer emojiId);

    void registerStudioEmojiListLog(Integer emojiId);
}
