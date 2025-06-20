package com.angkorchat.emoji.cms.domain.angkor.emoji.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.InvalidAdminUserException;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiFileDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiIdDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.EmojiNameDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.ModifyEmojiInfo;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.RegisterEmoji;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.RegisterMovingEmoji;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.TagDto;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response.*;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dao.mapper.CmsEmojiQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.emoji.exception.*;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.response.StudioTagDto;
import com.angkorchat.emoji.cms.domain.studio.emoji.exception.InvalidRequestException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.constant.ArtistStatus;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CmsEmojiService {
    private final CmsEmojiQueryMapper cmsEmojiQueryMapper;
    private final S3Service s3Service;

    public UploadFileResponse uploadFile(MultipartFile file, String name) {
        String url = s3Service.save(file, "emoji");

        UploadFileResponse res = new UploadFileResponse();
        res.setUrl(url);
        return res;
    }

    public Page<EmojiListDto> emojiList(Pageable pageable, String status, String emojiType, String searchKeyword) {
        return new PageImpl<>(cmsEmojiQueryMapper.emojiList(pageable, status, emojiType, searchKeyword),
                pageable,
                cmsEmojiQueryMapper.emojiListCount(status, emojiType, searchKeyword));
    }

    public EmojiDetail emojiDetail(Integer id) {
        return cmsEmojiQueryMapper.emojiDetail(id);
    }

    public Page<EmojiListDto> emojiRequestList(Pageable pageable, String status, String searchKeyword) {
        return new PageImpl<>(cmsEmojiQueryMapper.emojiRequestList(pageable, status, searchKeyword),
                pageable,
                cmsEmojiQueryMapper.emojiRequestListCount(status, searchKeyword));
    }

    public EmojiRequestDetail emojiRequestDetail(Integer id) {
        return cmsEmojiQueryMapper.emojiRequestDetail(id);
    }

    @Transactional
    public void registerEmoji(RegisterEmoji req) {
//        if(req.getEmojiFiles().size() != 16) {
//            throw new InvalidInputDataFormatException("16 emojiFiles required");
//        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        EmojiNameDto nameCheck = cmsEmojiQueryMapper.checkEmojiNameDuplicate(req.getEmojiNameEn(), req.getEmojiNameKm());

        if (nameCheck != null) {
            if (nameCheck.getEmojiNameEn().equals(req.getEmojiNameEn())) {
                throw new EmojiNameDuplicateException("emojiNameEn");
            }
            if (nameCheck.getEmojiNameKm().equals(req.getEmojiNameKm())) {
                throw new EmojiNameDuplicateException("emojiNameKm");
            }
        }


        Integer price = cmsEmojiQueryMapper.getEmojiPrice(req.getPointId());
        Integer originPrice = cmsEmojiQueryMapper.getEmojiPrice(req.getOriginPointId());
        if(price == null || originPrice == null) {
            throw new PointInfoNotFoundException();
        }
        if(price > originPrice) {
            throw new InvalidInputDataFormatException("Invalid Price Setting");
        }

        req.setPoint(price);

        // ******************* artist_master 등록 ******************//
        if (EmojiStatus.FOR_SALE.getValue().equals(req.getStatus())) {
            req.setOpenDt(LocalDateTime.now().toString());
        }
        req.setImageType(ImageType.STOP.getValue());
        req.setRegId(adminId);

        cmsEmojiQueryMapper.registerArtistEmojiMaster(req);

        Integer artistEmojiId = req.getArtistEmojiId();
        // *******************************************************//

        // artist_master main_file 등록
        String mainEmojiUrl = this.saveS3File(req.getMainFile(), artistEmojiId);
        String attachmentFileUrl = "";
        if (req.getAttachmentFile() != null) {
            attachmentFileUrl = this.saveS3File(req.getAttachmentFile(), artistEmojiId);
        }
        cmsEmojiQueryMapper.registerArtistEmojiMasterFile(artistEmojiId, mainEmojiUrl, null, attachmentFileUrl);

        // artist_master shortcut(tabFile) 등록
        String tabEmojiUrl = this.saveS3File(req.getTabFile(), artistEmojiId);
        cmsEmojiQueryMapper.registerArtistEmojiShortcut(artistEmojiId, tabEmojiUrl);

        // artist_emoji_list 파일 등록
        List<EmojiFileDto> fileList = new ArrayList<>();

        for (MultipartFile file : req.getEmojiFiles()) {
            String emojiUrl = this.saveS3File(file, artistEmojiId);
            EmojiFileDto fileDto = new EmojiFileDto();
            fileDto.setEmojiUrl(emojiUrl);
            fileDto.setMovingEmojiUrl(null);
            fileList.add(fileDto);
        }

        cmsEmojiQueryMapper.registerArtistEmojiList(artistEmojiId, fileList);

        // master 등록
        EmojiIdDto emojiIdInfo = new EmojiIdDto();
        emojiIdInfo.setArtistEmojiId(artistEmojiId);
        emojiIdInfo.setOriginPrice(originPrice);
        cmsEmojiQueryMapper.registerEmojiMaster(emojiIdInfo);

        // shortcut 등록
        cmsEmojiQueryMapper.registerEmojiShortcut(emojiIdInfo);

        // emoji List 등록
        cmsEmojiQueryMapper.registerEmojiList(emojiIdInfo);

        List<TagDto> originalTags = req.getTags();
        Set<String> seenLowercase = new HashSet<>();
        List<TagDto> distinctTags = new ArrayList<>();

        // 받아온 태그 중복제거
        for (TagDto tagDto : originalTags) {
            // 맨앞 # 지우기
            if (tagDto.getTag().startsWith("#")) {
                tagDto.setTag(tagDto.getTag().substring(1));
            }

            String lowerTag = tagDto.getTag().toLowerCase();
            if (seenLowercase.add(lowerTag)) { // add()가 true일 때만 추가
                distinctTags.add(tagDto);
            }
        }

        if (!distinctTags.isEmpty()) {
            List<TagDto> existingTagInfoList = cmsEmojiQueryMapper.hashTagInfoListByTags(distinctTags);

            // 태그 넣기
            Set<String> existingTagSet = new HashSet<>();
            for (TagDto tagInfo : existingTagInfoList) {
                existingTagSet.add(tagInfo.getTag().toLowerCase()); // 소문자로 통일
            }

            List<TagDto> newTags = new ArrayList<>();
            for (TagDto tag : distinctTags) {
                if (!existingTagSet.contains(tag.getTag().toLowerCase())) {
                    newTags.add(tag); // 비교는 소문자, 저장은 원본
                }
            }

            // ank_hashTag 등록
            if (!newTags.isEmpty()) {
                cmsEmojiQueryMapper.registerHashTags(newTags);
            }

            // 해당 태그들 태그 id 가져오기
            List<TagDto> tagInfoList = cmsEmojiQueryMapper.hashTagInfoListByTags(distinctTags);
            // 아티스트 태그에 저장
            cmsEmojiQueryMapper.registerArtistHashTags(artistEmojiId, tagInfoList);
        } else {
            throw new InvalidTagNameException("Prohibited tags detected");
        }

        // 관련 사이트 넣기
        if (req.getRelatedSites() != null && !req.getRelatedSites().isEmpty()) {
            cmsEmojiQueryMapper.registerArtistRelatedSites(artistEmojiId, req.getRelatedSites());
        }

        // 로깅 artist_emoji, emoji
        this.registerArtistEmojiLogs(artistEmojiId);
        this.registerEmojiLogs(emojiIdInfo.getId());
    }

    @Transactional
    public void registerMovingEmoji(RegisterMovingEmoji req) {
        if (req.getMovingEmojiFiles().size() != req.getEmojiFiles().size()) {
            throw new InvalidInputDataFormatException("emojiFiles");
        }

//        if(req.getMovingEmojiFiles().size() != 16) {
//            throw new InvalidInputDataFormatException("16 emojiFiles required");
//        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        EmojiNameDto nameCheck = cmsEmojiQueryMapper.checkEmojiNameDuplicate(req.getEmojiNameEn(), req.getEmojiNameKm());

        if (nameCheck != null) {
            if (nameCheck.getEmojiNameEn().equals(req.getEmojiNameEn())) {
                throw new EmojiNameDuplicateException("emojiNameEn");
            }
            if (nameCheck.getEmojiNameKm().equals(req.getEmojiNameKm())) {
                throw new EmojiNameDuplicateException("emojiNameKm");
            }
        }

        Integer price = cmsEmojiQueryMapper.getEmojiPrice(req.getPointId());
        Integer originPrice = cmsEmojiQueryMapper.getEmojiPrice(req.getOriginPointId());
        if(price == null || originPrice == null) {
            throw new PointInfoNotFoundException();
        }
        if(price > originPrice) {
            throw new InvalidInputDataFormatException("Invalid Price Setting");
        }

        req.setPoint(price);

        // ******************* artist_master 등록 ******************//
        if (EmojiStatus.FOR_SALE.getValue().equals(req.getStatus())) {
            req.setOpenDt(LocalDateTime.now().toString());
        }
        req.setImageType(ImageType.MOVING.getValue());
        req.setRegId(adminId);

        cmsEmojiQueryMapper.registerArtistEmojiMaster(req);

        Integer artistEmojiId = req.getArtistEmojiId();
        // *******************************************************//

        // artist_master main_file 등록
        String mainEmojiUrl = this.saveS3File(req.getMainFile(), artistEmojiId);
        String mainMovingEmojiUrl = this.saveS3File(req.getMovingMainFile(), artistEmojiId);
        String attachmentFileUrl = "";
        if (req.getAttachmentFile() != null) {
            attachmentFileUrl = this.saveS3File(req.getAttachmentFile(), artistEmojiId);
        }
        cmsEmojiQueryMapper.registerArtistEmojiMasterFile(artistEmojiId, mainEmojiUrl, mainMovingEmojiUrl, attachmentFileUrl);

        // artist_master shortcut(tabFile) 등록
        String tabEmojiUrl = this.saveS3File(req.getTabFile(), artistEmojiId);
        cmsEmojiQueryMapper.registerArtistEmojiShortcut(artistEmojiId, tabEmojiUrl);

        // artist_emoji_list 파일 등록
        List<EmojiFileDto> fileList = new ArrayList<>();

        int size = req.getEmojiFiles().size();
        for (int i = 0; i < size; i++) {
            String emojiUrl = this.saveS3File(req.getEmojiFiles().get(i), artistEmojiId);
            String movingEmojiUrl = this.saveS3File(req.getMovingEmojiFiles().get(i), artistEmojiId);
            EmojiFileDto fileDto = new EmojiFileDto();
            fileDto.setEmojiUrl(emojiUrl);
            fileDto.setMovingEmojiUrl(movingEmojiUrl);
            fileList.add(fileDto);
        }

        cmsEmojiQueryMapper.registerArtistEmojiList(artistEmojiId, fileList);

        // master 등록
        EmojiIdDto emojiIdInfo = new EmojiIdDto();
        emojiIdInfo.setArtistEmojiId(artistEmojiId);
        emojiIdInfo.setOriginPrice(originPrice);
        cmsEmojiQueryMapper.registerEmojiMaster(emojiIdInfo);

        // shortcut 등록
        cmsEmojiQueryMapper.registerEmojiShortcut(emojiIdInfo);

        // emoji List 등록
        cmsEmojiQueryMapper.registerEmojiList(emojiIdInfo);

        // [태그 넣기]
        List<TagDto> originalTags = req.getTags();
        Set<String> seenLowercase = new HashSet<>();
        List<TagDto> distinctTags = new ArrayList<>();

        // 받아온 태그 중복제거
        for (TagDto tagDto : originalTags) {
            // 맨앞 # 지우기
            if (tagDto.getTag().startsWith("#")) {
                tagDto.setTag(tagDto.getTag().substring(1));
            }

            String lowerTag = tagDto.getTag().toLowerCase();
            if (seenLowercase.add(lowerTag)) { // add()가 true일 때만 추가
                distinctTags.add(tagDto);
            }
        }

        if (!distinctTags.isEmpty()) {
            List<TagDto> existingTagInfoList = cmsEmojiQueryMapper.hashTagInfoListByTags(distinctTags);

            // 태그 넣기
            Set<String> existingTagSet = new HashSet<>();
            for (TagDto tagInfo : existingTagInfoList) {
                existingTagSet.add(tagInfo.getTag().toLowerCase()); // 소문자로 통일
            }

            List<TagDto> newTags = new ArrayList<>();
            for (TagDto tag : distinctTags) {
                if (!existingTagSet.contains(tag.getTag().toLowerCase())) {
                    newTags.add(tag); // 비교는 소문자, 저장은 원본
                }
            }

            // ank_hashTag 등록
            if (!newTags.isEmpty()) {
                cmsEmojiQueryMapper.registerHashTags(newTags);
            }

            // 해당 태그들 태그 id 가져오기
            List<TagDto> tagInfoList = cmsEmojiQueryMapper.hashTagInfoListByTags(distinctTags);
            // 아티스트 태그에 저장
            cmsEmojiQueryMapper.registerArtistHashTags(artistEmojiId, tagInfoList);
        } else {
            throw new InvalidTagNameException("Prohibited tags detected");
        }

        // 관련 사이트 넣기
        if (req.getRelatedSites() != null && !req.getRelatedSites().isEmpty()) {
            cmsEmojiQueryMapper.registerArtistRelatedSites(artistEmojiId, req.getRelatedSites());
        }

        // 로깅 artist_emoji, emoji
        this.registerArtistEmojiLogs(artistEmojiId);
        this.registerEmojiLogs(emojiIdInfo.getId());
    }

    public List<ArtistNameDto> artistNameList() {
        return cmsEmojiQueryMapper.artistNameList(ArtistStatus.APPROVED.getValue());
    }

    public List<PointInfoDto> emojiPointInfoList() {
        return cmsEmojiQueryMapper.emojiPointInfoList();
    }

    @Transactional
    public void deleteEmoji(Integer id, Integer artistEmojiId) {
        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }

        cmsEmojiQueryMapper.changeEmojiMasterStatus(id, adminId, EmojiStatus.DELETED.getValue(), null);
        cmsEmojiQueryMapper.changeArtistEmojiMasterStatus(artistEmojiId, adminId, EmojiStatus.DELETED.getValue(), null);

        this.registerArtistEmojiLogs(artistEmojiId);
        this.registerEmojiLogs(id);
    }

    @Transactional
    public void updateEmojiRequestStatus(ModifyEmojiInfo req) {
        if(!Set.of(EmojiStatus.FOR_SALE.getValue(),EmojiStatus.REJECTED.getValue(),EmojiStatus.PAUSED.getValue()).contains(req.getStatus())) {
            throw new InvalidRequestStatusChangeException();
        }

        String status = cmsEmojiQueryMapper.getArtistEmojiStatus(req.getArtistEmojiId());

        if(status == null) {
            throw new EmojiNotFoundException();
        }

        if(!status.equals(EmojiStatus.IN_REVIEW.getValue())) {
            throw new InvalidRequestStatusChangeException();
        }

        Integer adminId = SecurityUtils.getLoginUserNo();

        if (adminId == null) {
            throw new InvalidAdminUserException();
        }
        req.setAdminId(adminId);

        if (req.getStatus().equals(EmojiStatus.FOR_SALE.getValue())){
            Integer price = cmsEmojiQueryMapper.getEmojiPrice(req.getPointId());

            if (price == null) {
                throw new PointInfoNotFoundException();
            }

            req.setPoint(price);
            req.setOpenDt(LocalDateTime.now().toString());

            // artistEmoji 정보변경
            cmsEmojiQueryMapper.modifyArtistEmojiMasterInfo(req);

            // master 등록
            EmojiIdDto emojiIdInfo = new EmojiIdDto();
            emojiIdInfo.setArtistEmojiId(req.getArtistEmojiId());
            cmsEmojiQueryMapper.registerEmojiMaster(emojiIdInfo);
            // shortcut 등록
            cmsEmojiQueryMapper.registerEmojiShortcut(emojiIdInfo);
            // emoji List 등록
            cmsEmojiQueryMapper.registerEmojiList(emojiIdInfo);
            // 로깅
            this.registerArtistEmojiLogs(req.getArtistEmojiId());
            this.registerEmojiLogs(emojiIdInfo.getId());
        } else if (req.getStatus().equals(EmojiStatus.REJECTED.getValue())) {
            cmsEmojiQueryMapper.changeArtistEmojiMasterStatus(req.getArtistEmojiId(), adminId, req.getStatus(), req.getStatusReason());
            this.registerArtistEmojiLogs(req.getArtistEmojiId());
        }
//
//        else if (req.getStatus().equals(EmojiStatus.PAUSED.getValue())) {
//            Integer price = cmsEmojiQueryMapper.getEmojiPrice(req.getPointId());
//
//            if (price == null) {
//                throw new PointInfoNotFoundException();
//            }
//            req.setPoint(price);
//
//            // artistEmoji 정보변경
//            cmsEmojiQueryMapper.modifyArtistEmojiMasterInfo(req);
//
//            // master 등록
//            EmojiIdDto emojiIdInfo = new EmojiIdDto();
//            emojiIdInfo.setArtistEmojiId(req.getArtistEmojiId());
//            cmsEmojiQueryMapper.registerEmojiMaster(emojiIdInfo);
//            // shortcut 등록
//            cmsEmojiQueryMapper.registerEmojiShortcut(emojiIdInfo);
//            // emoji List 등록
//            cmsEmojiQueryMapper.registerEmojiList(emojiIdInfo);
//            // 로깅
//            this.registerArtistEmojiLogs(req.getArtistEmojiId());
//            this.registerEmojiLogs(emojiIdInfo.getId());
//        }
    }

    private String saveS3File(MultipartFile file, Integer id) {
        return s3Service.save(file, "artist/emoji/" + id);
    }

    @Transactional
    private void registerEmojiLogs(Integer emojiMasterId) {
        // emoji master Log
        cmsEmojiQueryMapper.registerEmojiMasterLog(emojiMasterId);
        // emoji shortcut log
        cmsEmojiQueryMapper.registerEmojiShortcutLog(emojiMasterId);
        // emoji list log
        cmsEmojiQueryMapper.registerEmojiListLog(emojiMasterId);
    }

    @Transactional
    private void registerArtistEmojiLogs(Integer artistEmojiMasterId) {
        // artist emoji master Log
        cmsEmojiQueryMapper.registerArtistEmojiMasterLog(artistEmojiMasterId);
        // artist emoji shortcut log
        cmsEmojiQueryMapper.registerArtistEmojiShortcutLog(artistEmojiMasterId);
        // artist emoji list log
        cmsEmojiQueryMapper.registerArtistEmojiListLog(artistEmojiMasterId);
    }
}
