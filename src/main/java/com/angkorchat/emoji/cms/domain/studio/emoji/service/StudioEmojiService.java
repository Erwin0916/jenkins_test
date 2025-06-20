package com.angkorchat.emoji.cms.domain.studio.emoji.service;


import com.angkorchat.emoji.cms.domain.angkor.auth.exception.AuthenticationFailException;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiFileDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.exception.InvalidTagNameException;
import com.angkorchat.emoji.cms.domain.studio.emoji.dao.mapper.StudioEmojiQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.request.*;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.response.*;
import com.angkorchat.emoji.cms.domain.studio.emoji.exception.InvalidRequestException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.constant.EmojiStatus;
import com.angkorchat.emoji.cms.global.constant.ImageType;
import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;
import com.angkorchat.emoji.cms.infra.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StudioEmojiService {
    private final StudioEmojiQueryMapper studioEmojiQueryMapper;
    private final S3Service s3Service;

    public StudioArtistEmojiInfo artistEmojiList(Pageable pageable, Boolean proposal, String status, String imageType) {
        Integer artistId = SecurityUtils.getStudioLoginUserNo();
        if(artistId == null) {
            throw new AuthenticationFailException();
        }

        StudioArtistEmojiInfo emojiInfo = new StudioArtistEmojiInfo();
        EmojiStatusCount emojiStatusCount = studioEmojiQueryMapper.emojiStatusCount(artistId);
        emojiInfo.setEmojiStatusCount(emojiStatusCount);

        Page<StudioArtistEmojiList> studioArtistEmojiListPage = new PageImpl<>(studioEmojiQueryMapper.studioArtistEmojiList(pageable, proposal, artistId, status, imageType),
                pageable,
                studioEmojiQueryMapper.studioArtistEmojiListCount(proposal, artistId, status, imageType));

        emojiInfo.setStudioArtistEmojiListPage(studioArtistEmojiListPage);

        return emojiInfo;
    }

    public StudioArtistEmojiDetail studioArtistEmojiDetail(Integer id) {
        Integer artistId = SecurityUtils.getStudioLoginUserNo();
        if(artistId == null) {
            throw new AuthenticationFailException();
        }

        return studioEmojiQueryMapper.studioArtistEmojiDetail(id, artistId);
    }

    @Transactional
    public void registerStudioEmoji(RegisterStudioEmoji req) {
//        if(req.getEmojiFiles().size() != 16) {
//            throw new InvalidInputDataFormatException("16 emojiFiles required");
//        }

        // 태그 block 리스트 확인하여 제외

        Integer artistId = SecurityUtils.getStudioLoginUserNo();
        if(artistId == null) {
            throw new AuthenticationFailException();
        }

        req.setArtistId(artistId);

        for (StudioTagDto hashtag : req.getTags()) {
            // 맨앞 # 지우기
            if (hashtag.getTag().startsWith("#")) {
                hashtag.setTag(hashtag.getTag().substring(1));
            }

            Integer cnt = studioEmojiQueryMapper.getTagNameFilterList(hashtag.getTag());
            if (cnt != null) {
                throw new InvalidTagNameException("Prohibited tags detected: " + hashtag.getTag());
            }
        }

        // ******************* artist_master 등록 ******************//
        req.setImageType(ImageType.STOP.getValue());
        req.setStatus(EmojiStatus.IN_REVIEW.getValue());
        studioEmojiQueryMapper.registerArtistStudioEmojiMaster(req);

        Integer emojiId = req.getId();
        // *******************************************************//

        // artist_master main_file 등록
        studioEmojiQueryMapper.registerArtistStudioEmojiMasterFile(emojiId, req.getMainImageUrl(), null, req.getAttachmentFileUrl());

        // artist_master shortcut(tabFile) 등록
        studioEmojiQueryMapper.registerArtistStudioEmojiShortcut(emojiId, req.getTabImageUrl());

        // artist_emoji_list 파일 등록
        List<EmojiFileDto> fileList = new ArrayList<>();

        for (String fileUrl : req.getEmojiUrls()) {
            EmojiFileDto fileDto = new EmojiFileDto();
            fileDto.setEmojiUrl(fileUrl);
            fileDto.setMovingEmojiUrl(null);
            fileList.add(fileDto);
        }

        studioEmojiQueryMapper.registerArtistStudioEmojiList(emojiId, fileList);

        List<StudioTagDto> originalTags = req.getTags();
        Set<String> seenLowercase = new HashSet<>();
        List<StudioTagDto> distinctTags = new ArrayList<>();

        // 받아온 태그 중복제거
        for (StudioTagDto tagDto : originalTags) {
            if (tagDto.getTag().startsWith("#")) {
                tagDto.setTag(tagDto.getTag().substring(1));
            }

            Integer cnt = studioEmojiQueryMapper.getTagNameFilterList(tagDto.getTag());
            if (cnt != null) {
                throw new InvalidTagNameException("Prohibited tags detected: " + tagDto.getTag());
            }

            String lowerTag = tagDto.getTag().toLowerCase();
            if (seenLowercase.add(lowerTag)) { // add()가 true일 때만 추가
                distinctTags.add(tagDto);
            }
        }

        if (!distinctTags.isEmpty()) {
            List<StudioTagDto> existingTagInfoList = studioEmojiQueryMapper.studioHashTagInfoListByTags(distinctTags);

            Set<String> existingTagSet = new HashSet<>();
            for (StudioTagDto tagInfo : existingTagInfoList) {
                existingTagSet.add(tagInfo.getTag().toLowerCase()); // 소문자로 통일
            }

            List<StudioTagDto> newTags = new ArrayList<>();
            for (StudioTagDto tag : distinctTags) {
                if (!existingTagSet.contains(tag.getTag().toLowerCase())) {
                    newTags.add(tag); // 비교는 소문자, 저장은 원본
                }
            }

            // ank_hashTag 등록
            if (!newTags.isEmpty()) {
                studioEmojiQueryMapper.registerStudioHashTags(newTags);
            }
            // 신규 태그 저장
            // 해당 태그들 태그 id 가져오기
            List<StudioTagDto> tagInfoList = studioEmojiQueryMapper.studioHashTagInfoListByTags(distinctTags);

            // 아티스트 태그에 저장
            studioEmojiQueryMapper.registerArtistStudioHashTags(emojiId, tagInfoList);
        } else {
            throw new InvalidTagNameException("Prohibited tags detected");
        }
        // 관련 사이트 넣기
        if (req.getRelatedSites() != null && !req.getRelatedSites().isEmpty()) {
            studioEmojiQueryMapper.registerArtistStudioRelatedSites(emojiId, req.getRelatedSites());
        }

        // 로깅 artist_emoji, emoji
        this.registerStudioArtistEmojiLogs(emojiId);
    }

    @Transactional
    public void modifyStudioEmoji(ModifyStudioEmoji req) {
        Integer artistId = SecurityUtils.getStudioLoginUserNo();
        if(artistId == null) {
            throw new AuthenticationFailException();
        }

        req.setArtistId(artistId);

        String status = studioEmojiQueryMapper.checkStudioEmojiStatus(req.getId());
        if (!status.equals(EmojiStatus.REJECTED.getValue())) {
            throw new InvalidRequestException();
        }

        // ******************* artist_master 등록 ******************//
        req.setStatus(EmojiStatus.IN_REVIEW.getValue());

        Integer emojiId = req.getId();
        // *******************************************************//

        // 정보 수정 쿼리 호출
        studioEmojiQueryMapper.modifyStudioEmoji(req);

        if (req.getTabImageUrl() != null) {
            // 업데이트 탭파일
            studioEmojiQueryMapper.modifyArtistStudioEmojiShortcut(emojiId, req.getTabImageUrl());
        }

        if (req.getEmojiFilesInfo() != null) {
            // 업데이트 이모지 리스트
            studioEmojiQueryMapper.modifyArtistStudioEmojiList(emojiId, req.getEmojiFilesInfo());
        }

        // 태그 지우기
        studioEmojiQueryMapper.deleteArtistEmojiHashTags(emojiId);

        List<StudioTagDto> originalTags = req.getTags();
        Set<String> seenLowercase = new HashSet<>();
        List<StudioTagDto> distinctTags = new ArrayList<>();

        // 받아온 태그 중복제거
        for (StudioTagDto tagDto : originalTags) {
            if (tagDto.getTag().startsWith("#")) {
                tagDto.setTag(tagDto.getTag().substring(1));
            }

            Integer cnt = studioEmojiQueryMapper.getTagNameFilterList(tagDto.getTag());
            if (cnt != null) {
                throw new InvalidTagNameException("Prohibited tags detected: " + tagDto.getTag());
            }

            String lowerTag = tagDto.getTag().toLowerCase();
            if (seenLowercase.add(lowerTag)) { // add()가 true일 때만 추가
                distinctTags.add(tagDto);
            }
        }

        if (!distinctTags.isEmpty()) {
            List<StudioTagDto> existingTagInfoList = studioEmojiQueryMapper.studioHashTagInfoListByTags(distinctTags);

            Set<String> existingTagSet = new HashSet<>();
            for (StudioTagDto tagInfo : existingTagInfoList) {
                existingTagSet.add(tagInfo.getTag().toLowerCase()); // 소문자로 통일
            }

            List<StudioTagDto> newTags = new ArrayList<>();
            for (StudioTagDto tag : distinctTags) {
                if (!existingTagSet.contains(tag.getTag().toLowerCase())) {
                    newTags.add(tag); // 비교는 소문자, 저장은 원본
                }
            }

            // ank_hashTag 등록
            if (!newTags.isEmpty()) {
                studioEmojiQueryMapper.registerStudioHashTags(newTags);
            }
            // 신규 태그 저장
            // 해당 태그들 태그 id 가져오기
            List<StudioTagDto> tagInfoList = studioEmojiQueryMapper.studioHashTagInfoListByTags(distinctTags);

            // 아티스트 태그에 저장
            studioEmojiQueryMapper.registerArtistStudioHashTags(emojiId, tagInfoList);
        } else {
            throw new InvalidTagNameException("Prohibited tags detected");
        }
        // 관련 사이트 넣기
        if (req.getRelatedSites() != null && !req.getRelatedSites().isEmpty()) {
            // 사이트 지우기
            studioEmojiQueryMapper.deleteArtistStudioRelatedSites(emojiId);
            // 사이트 저장
            studioEmojiQueryMapper.registerArtistStudioRelatedSites(emojiId, req.getRelatedSites());
        }

        // 로깅 artist_emoji, emoji
        this.registerStudioArtistEmojiLogs(emojiId);
    }

    @Transactional
    public void modifyStudioEmojiStatus(ModifyStudioEmojiStatus req) {
        Integer artistId = SecurityUtils.getStudioLoginUserNo();
        if(artistId == null) {
            throw new AuthenticationFailException();
        }

        req.setArtistId(artistId);

        String status = studioEmojiQueryMapper.checkStudioEmojiStatus(req.getId());
        if (!Set.of(EmojiStatus.FOR_SALE.getValue(), EmojiStatus.PAUSED.getValue()).contains(status)) {
            throw new InvalidRequestException();
        }

        Integer id = studioEmojiQueryMapper.getStudioEmojiId(req.getId());
        studioEmojiQueryMapper.modifyStudioEmojiStatus(req);


        // 로깅 artist_emoji, emoji
        this.registerStudioArtistEmojiLogs(req.getId());
        this.registerStudioEmojiLogs(id);
    }

    @Transactional
    public void registerMovingEmoji(RegisterStudioMovingEmoji req) {
        if (req.getMovingEmojiUrls().size() != req.getEmojiUrls().size()) {
            throw new InvalidInputDataFormatException("emojiFiles");
        }

        Integer artistId = SecurityUtils.getStudioLoginUserNo();
        if(artistId == null) {
            throw new AuthenticationFailException();
        }

        req.setArtistId(artistId);

//        if(req.getMovingEmojiFiles().size() != 16) {
//            throw new InvalidInputDataFormatException("16 emojiFiles required");
//        }

        // ******************* artist_master 등록 ******************//
        req.setImageType(ImageType.MOVING.getValue());
        req.setStatus(EmojiStatus.IN_REVIEW.getValue());
        studioEmojiQueryMapper.registerArtistStudioEmojiMaster(req);

        Integer emojiId = req.getId();
        // *******************************************************//

        // artist_master main_file 등록
        studioEmojiQueryMapper.registerArtistStudioEmojiMasterFile(emojiId, req.getMainImageUrl(), req.getMovingMainImageUrl(), req.getAttachmentFileUrl());

        // artist_master shortcut(tabFile) 등록
        studioEmojiQueryMapper.registerArtistStudioEmojiShortcut(emojiId, req.getTabImageUrl());

        // artist_emoji_list 파일 등록
        List<EmojiFileDto> fileList = new ArrayList<>();

        int size = req.getEmojiUrls().size();
        for (int i = 0; i < size; i++) {
            EmojiFileDto fileDto = new EmojiFileDto();
            fileDto.setEmojiUrl(req.getEmojiUrls().get(i));
            fileDto.setMovingEmojiUrl(req.getMovingEmojiUrls().get(i));
            fileList.add(fileDto);
        }

        studioEmojiQueryMapper.registerArtistStudioEmojiList(emojiId, fileList);

        // [태그 넣기]
        List<StudioTagDto> originalTags = req.getTags();
        Set<String> seenLowercase = new HashSet<>();
        List<StudioTagDto> distinctTags = new ArrayList<>();


        // 태그 block 리스트 확인하여 제외
        for (StudioTagDto hashtag : req.getTags()) {
            // 맨앞 # 지우기

            Integer cnt = studioEmojiQueryMapper.getTagNameFilterList(hashtag.getTag());
            if (cnt != null) {
                throw new InvalidTagNameException("Prohibited tags detected: " + hashtag.getTag());
            }
        }

        // 받아온 태그 중복제거
        for (StudioTagDto tagDto : originalTags) {
            if (tagDto.getTag().startsWith("#")) {
                tagDto.setTag(tagDto.getTag().substring(1));
            }

            Integer cnt = studioEmojiQueryMapper.getTagNameFilterList(tagDto.getTag());
            if (cnt != null) {
                throw new InvalidTagNameException("Prohibited tags detected: " + tagDto.getTag());
            }

            String lowerTag = tagDto.getTag().toLowerCase();
            if (seenLowercase.add(lowerTag)) { // add()가 true일 때만 추가
                distinctTags.add(tagDto);
            }
        }

        if (!distinctTags.isEmpty()) {
            List<StudioTagDto> existingTagInfoList = studioEmojiQueryMapper.studioHashTagInfoListByTags(distinctTags);

            Set<String> existingTagSet = new HashSet<>();
            for (StudioTagDto tagInfo : existingTagInfoList) {
                existingTagSet.add(tagInfo.getTag().toLowerCase()); // 소문자로 통일
            }

            List<StudioTagDto> newTags = new ArrayList<>();
            for (StudioTagDto tag : distinctTags) {
                if (!existingTagSet.contains(tag.getTag().toLowerCase())) {
                    newTags.add(tag); // 비교는 소문자, 저장은 원본
                }
            }

            // ank_hashTag 등록
            if (!newTags.isEmpty()) {
                studioEmojiQueryMapper.registerStudioHashTags(newTags);
            }
            // 신규 태그 저장
            // 해당 태그들 태그 id 가져오기
            List<StudioTagDto> tagInfoList = studioEmojiQueryMapper.studioHashTagInfoListByTags(distinctTags);

            // 아티스트 태그에 저장
            studioEmojiQueryMapper.registerArtistStudioHashTags(emojiId, tagInfoList);

        } else {
            throw new InvalidTagNameException("Prohibited tags detected");
        }
        // 관련 사이트 넣기
        if (req.getRelatedSites() != null && !req.getRelatedSites().isEmpty()) {
            studioEmojiQueryMapper.registerArtistStudioRelatedSites(emojiId, req.getRelatedSites());
        }

        // 로깅 artist_emoji, emoji
        this.registerStudioArtistEmojiLogs(emojiId);
    }

    @Transactional
    private void registerStudioArtistEmojiLogs(Integer emojiId) { // ank_artist_emoji_master id
        // artist emoji master Log
        studioEmojiQueryMapper.registerArtistStudioEmojiMasterLog(emojiId);
        // artist emoji shortcut log
        studioEmojiQueryMapper.registerArtistStudioEmojiShortcutLog(emojiId);
        // artist emoji list log
        studioEmojiQueryMapper.registerArtistStudioEmojiListLog(emojiId);
    }

    @Transactional
    private void registerStudioEmojiLogs(Integer emojiMasterId) { // ank_emoji_master id
        // emoji master Log
        studioEmojiQueryMapper.registerStudioEmojiMasterLog(emojiMasterId);
        // emoji shortcut log
        studioEmojiQueryMapper.registerStudioEmojiShortcutLog(emojiMasterId);
        // emoji list log
        studioEmojiQueryMapper.registerStudioEmojiListLog(emojiMasterId);
    }
}
