package com.angkorchat.emoji.cms.domain.angkor.emoji.api;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.ModifyEmojiInfo;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.RegisterEmoji;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.RegisterMovingEmoji;
import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response.*;
import com.angkorchat.emoji.cms.domain.angkor.emoji.service.CmsEmojiService;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "CMS Emoji API", description = "Emoji 관리 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("cms/emoji")
public class CmsEmojiApi {
    private final CmsEmojiService cmsEmojiService;

    /**
     * 사진 파일 등록 주소 반환
     **/
    @Operation(summary = "파일 이미지 임시등록 - Emoji 용 아닙니다! 사용하지 마세요! ", description = "파일 이미지를 등록하고 파일 링크를 받는다. common/folderName 경로에 저장  - Emoji 용 아닙니다! 사용하지 마세요!", tags = "CMS Emoji API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UploadFileResponse uploadFile(@RequestPart(name = "file") MultipartFile file,
                                         @RequestParam(name = "folderName") String folderName) {

        return cmsEmojiService.uploadFile(file, folderName);
    }

    /**
     * Emoji List
     **/
    @Operation(summary = "Emoji 리스트", description = "Emoji 리스트를 조회한다.", tags = "CMS Emoji API", parameters = {
            @Parameter(name = "status", description = "상태 타입(major: 002, minor: 001(For Sale), 002(Paused)"),
            @Parameter(name = "emojiType", description = "이모지 타입(D: 기본이모지, F: 무료이모지,  P: 유료이모지, E: 이벤트이모지)"),
            @Parameter(name = "tag", description = "태그로 검색"),
            @Parameter(name = "searchKeyword", description = "검색어 : emojiName, artistName")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Page<EmojiListDto> emojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                        @Size(max = 150) @RequestParam(name = "status", required = false) String status,
                                        @Size(max = 150) @RequestParam(name = "emojiType", required = false) String emojiType,
                                        @Size(max = 150) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());
//
        return cmsEmojiService.emojiList(pageable, status, emojiType, searchKeyword);
    }

    /**
     * Emoji Detail
     **/
    @Operation(summary = "Emoji Detail - Jenkins Test 수정사항", description = "Emoji Detail 을 조회한다. - Jenkins Test 수정사항", tags = "CMS Emoji API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public EmojiDetail emojiDetail(@Parameter(name = "id", description = "Emoji ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return cmsEmojiService.emojiDetail(id);
    }

    /**
     * 고정 이미지 Emoji 등록
     **/
    @Operation(summary = "고정 이미지 Emoji 등록 (Main File, Tab File, Emoji Files)", description = "고정 이미지 Emoji 를등록한다.", tags = "CMS Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerEmoji(@Valid @RequestPart(name = "req") RegisterEmoji req, @RequestPart(name = "mainFile") MultipartFile mainFile, @RequestPart(name = "tabFile") MultipartFile tabFile,
                                           @RequestPart(name = "emojiFiles") List<MultipartFile> emojiFiles, @RequestPart(name = "attachmentFile", required = false) MultipartFile attachmentFile) {
        req.setMainFile(mainFile);
        req.setTabFile(tabFile);
        req.setEmojiFiles(emojiFiles);
        req.setAttachmentFile(attachmentFile);
        cmsEmojiService.registerEmoji(req);

        return ResponseEntity.ok().build();
    }

    /**
     * 움직이는 이미지 Emoji 등록
     **/
    @Operation(summary = "움직이는 이미지 Emoji 등록 (Main File, Tab File, Emoji Files)", description = "움직이는 이미지 Emoji 를등록한다.", tags = "CMS Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/moving", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerMovingEmoji(@Valid @RequestPart(name = "req") RegisterMovingEmoji req, @RequestPart(name = "movingMainFile") MultipartFile movingMainFile, @RequestPart(name = "mainFile") MultipartFile mainFile, @RequestPart(name = "tabFile") MultipartFile tabFile,
                                                 @RequestPart(name = "movingEmojiFiles") List<MultipartFile> movingEmojiFiles, @RequestPart(name = "emojiFiles") List<MultipartFile> emojiFiles, @RequestPart(name = "attachmentFile", required = false) MultipartFile attachmentFile) {
        req.setMainFile(mainFile);
        req.setMovingMainFile(movingMainFile);
        req.setTabFile(tabFile);
        req.setEmojiFiles(emojiFiles);
        req.setMovingEmojiFiles(movingEmojiFiles);
        req.setAttachmentFile(attachmentFile);
        cmsEmojiService.registerMovingEmoji(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Artist Name List
     **/
    @Operation(summary = "Emoji Artist 이름 정보 리스트", description = "Emoji Artist 리스트를 조회한다.", tags = "CMS Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/artist/name/list")
    public List<ArtistNameDto> artistNameList() {
        return cmsEmojiService.artistNameList();
    }

    /**
     * Emoji Point 가격 정보 리스트
     **/
    @Operation(summary = "Emoji Point 가격 정보 리스트", description = "Emoji Point 가격 정보 리스트를 조회한다.", tags = "CMS Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/point/list")
    public List<PointInfoDto> emojiPointInfoList() {
        return cmsEmojiService.emojiPointInfoList();
    }

    /**
     * Emoji 삭제
     **/
    @Operation(summary = "Emoji 삭제", description = "Emoji 삭제 (status : Deleted(002003) 으로 변경)", tags = "CMS Emoji API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmoji(@Parameter(name = "id", description = "Emoji ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                         @Parameter(name = "artistEmojiId", description = "Artist Emoji ID") @Min(value = 1) @RequestParam(name = "artistEmojiId") Integer artistEmojiId) {
        cmsEmojiService.deleteEmoji(id, artistEmojiId);

        return ResponseEntity.ok().build();
    }


    /**
     * Artist(Request) Emoji 요청 승인/거절
     **/
    @Operation(summary = "Artist(Request) Emoji 요청 승인/거절", description = "Artist(Request) Emoji 요청을 승인/거절 한다.\n" +
            "\n\n For Sale(승인 및 판매 상태) - openDt 를 제외한 모든 항목을 입력하여 요청한다.\n" +
            "\n Paused(승인 및 대기 상태) 모든 항목을 입력하여 요청 입력한 openDt 에 이모티콘 판매를 시작한다.\n" +
            "\n Reject(거절) 상태만 필수로 입력한다.", tags = "CMS Emoji API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/request/{id}/status")
    public ResponseEntity<?> updateEmojiRequestStatus(@Parameter(name = "id", description = "Artist Emoji ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                      @RequestBody @Valid ModifyEmojiInfo req) {
        req.setArtistEmojiId(id);
        cmsEmojiService.updateEmojiRequestStatus(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Artist(Request) Emoji Request List
     **/
    @Operation(summary = "Artist(Request) Emoji 승인 요청 리스트", description = "Artist(Request) Emoji 승인 요청 리스트를 조회한다.", tags = "CMS Emoji API", parameters = {
            @Parameter(name = "status", description = "상태 타입(major: 002, minor: 004(In Review), 005(Denied)"),
            @Parameter(name = "searchKeyword", description = "검색어 : emojiName, artistName")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/request/list")
    public Page<EmojiListDto> emojiRequestList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                        @Size(max = 150) @RequestParam(name = "status", required = false) String status,
                                        @Size(max = 150) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return cmsEmojiService.emojiRequestList(pageable, status, searchKeyword);
    }

    /**
     * Artist(Request) Emoji Request Detail
     **/
    @Operation(summary = "Artist(Request) Emoji Detail", description = "Artist(Request) Emoji Detail 을 조회한다.", tags = "CMS Emoji API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/request/{id}")
    public EmojiRequestDetail emojiRequestDetail(@Parameter(name = "id", description = "Artist Emoji ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return cmsEmojiService.emojiRequestDetail(id);
    }
}
