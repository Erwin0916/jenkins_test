package com.angkorchat.emoji.cms.domain.angkor.emoji.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiFileDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiIdDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiNameDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.ModifyEmojiInfo;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.RegisterEmoji;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.TagDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CmsEmojiQueryMapper {
    List<EmojiListDto> emojiList(@Param("pageable") Pageable pageable,
                                 @Param("status") String status,
                                 @Param("emojiType") String emojiType,
                                 @Param("searchKeyword") String searchKeyword);

    long emojiListCount(@Param("status") String status,
                        @Param("emojiType") String emojiType,
                        @Param("searchKeyword") String searchKeyword);

    EmojiDetail emojiDetail(Integer id);

    List<EmojiListDto> emojiRequestList(@Param("pageable") Pageable pageable,
                                        @Param("status") String status,
                                        @Param("searchKeyword") String searchKeyword);

    long emojiRequestListCount(@Param("status") String status,
                               @Param("searchKeyword") String searchKeyword);

    EmojiRequestDetail emojiRequestDetail(Integer id);

    void registerArtistEmojiMaster(RegisterEmoji req);

    void registerArtistEmojiMasterFile(@Param("id") Integer id,
                                       @Param("mainEmojiUrl") String mainEmojiUrl,
                                       @Param("mainMovingEmojiUrl") String mainMovingEmojiUrl,
                                       @Param("attachmentFileUrl") String attachmentFileUrl);

    void registerArtistEmojiShortcut(@Param("id") Integer id,
                                     @Param("tabEmojiUrl") String tabEmojiUrl);

    void registerArtistEmojiList(@Param("id") Integer id,
                                 @Param("fileList") List<EmojiFileDto> fileList);

    void registerEmojiMaster(EmojiIdDto req);

    void registerEmojiShortcut(EmojiIdDto req);

    void registerEmojiList(EmojiIdDto req);

    EmojiNameDto checkEmojiNameDuplicate(@Param("emojiNameEn") String emojiNameEn,
                                         @Param("emojiNameKm") String emojiNameKm);

    void registerHashTags(@Param("tags") List<TagDto> tags);

    List<TagDto> hashTagInfoListByTags(@Param("tags") List<TagDto> tags);

    void registerArtistHashTags(@Param("emojiId") Integer emojiId,
                                @Param("tags") List<TagDto> tags);

    void registerArtistRelatedSites(@Param("emojiId") Integer emojiId,
                                    @Param("sites") List<String> sites);

    List<ArtistNameDto> artistNameList(Integer artistStatus);

    List<PointInfoDto> emojiPointInfoList();

    Integer getEmojiPrice(Integer id);

    void changeEmojiMasterStatus(@Param("id") Integer id,
                                 @Param("adminId") Integer adminId,
                                 @Param("status") String status,
                                 @Param("statusReason") String statusReason);

    void changeArtistEmojiMasterStatus(@Param("id") Integer id,
                                       @Param("adminId") Integer adminId,
                                       @Param("status") String status,
                                       @Param("statusReason") String statusReason);

    String getArtistEmojiStatus(Integer id);

    void modifyArtistEmojiMasterInfo(ModifyEmojiInfo req);

    void registerArtistEmojiMasterLog(Integer id);

    void registerArtistEmojiShortcutLog(Integer id);

    void registerArtistEmojiListLog(Integer id);

    void registerEmojiMasterLog(Integer id);

    void registerEmojiShortcutLog(Integer id);

    void registerEmojiListLog(Integer id);
}
