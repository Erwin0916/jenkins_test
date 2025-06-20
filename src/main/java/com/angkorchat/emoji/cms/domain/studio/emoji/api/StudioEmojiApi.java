package com.angkorchat.emoji.cms.domain.studio.emoji.api;

import com.angkorchat.emoji.cms.domain.studio.emoji.dto.request.*;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.response.StudioArtistEmojiDetail;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.response.StudioArtistEmojiInfo;
import com.angkorchat.emoji.cms.domain.studio.emoji.service.StudioEmojiService;

import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Studio Emoji API", description = "Studio Emoji API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("studio/emoji")
public class StudioEmojiApi {
    private final StudioEmojiService studioEmojiService;
    private static final Logger log = LoggerFactory.getLogger(StudioEmojiApi.class);

    /**
     * Emoji List
     **/
    @Operation(summary = "Artist 가 승인 받은/제안한 emoji List", description = "Artist 가 받은/제안한 emoji List 를 조회한다." +
            "\n proposal(true: 제안 리스트(004(In Review), 005(Rejected)), false: 승인 받은 리스트(001(For Sale), 002(Paused))", tags = "Studio Emoji API", parameters = {
            @Parameter(name = "proposal", description = "proposal(true: 제안 리스트, false: 승인 받은 리스트)"),
            @Parameter(name = "status", description = "상태 타입(major: 002, minor: 001(For Sale), 002(Paused), 004(In Review), 005(Rejected)"),
            @Parameter(name = "imageType", description = "S : 정지 이모지, M : 무빙 이모지")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public StudioArtistEmojiInfo artistEmojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                 @RequestParam(name = "proposal") Boolean proposal,
                                                 @Size(max = 150) @RequestParam(name = "status", required = false) String status,
                                                 @Size(max = 150) @RequestParam(name = "imageType", required = false) String imageType) {
//        CommonUtils.checkValidUser(artistId);

        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return studioEmojiService.artistEmojiList(pageable, proposal, status, imageType);
    }

    /**
     * Emoji Detail
     **/
    @Operation(summary = "Artist Emoji Detail", description = "Artist Emoji Detail 을 조회한다.", tags = "Studio Emoji API", parameters = {
            @Parameter(name = "artistId", description = "artistId")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public StudioArtistEmojiDetail studioArtistEmojiDetail(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id) {
//        CommonUtils.checkValidUser(artistId);

        return studioEmojiService.studioArtistEmojiDetail(id);
    }

    /**
     * STUDIO 고정 이미지 Emoji 등록
     **/
    @Operation(summary = "고정 이미지 Emoji 등록 (Main File, Tab File, Emoji Files)", description = "고정 이미지 Emoji 를등록한다.", tags = "Studio Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> registerStudioEmoji(@Valid @RequestBody RegisterStudioEmoji req) {
//        CommonUtils.checkValidUser(req.getArtistId());

        studioEmojiService.registerStudioEmoji(req);

        return ResponseEntity.ok().build();
    }

    /**
     * STUDIO 이미지 Emoji 수정
     **/
    @Operation(summary = "이미지 Emoji 수정", description = "이미지 Emoji 를 수정한다.", tags = "Studio Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ResponseEntity<?> modifyStudioEmoji(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                               @Valid @RequestBody ModifyStudioEmoji req) {
//        CommonUtils.checkValidUser(req.getArtistId());

        req.setId(id);
        studioEmojiService.modifyStudioEmoji(req);

        return ResponseEntity.ok().build();
    }

    /**
     * STUDIO 이미지 Emoji 상태 수정
     **/
    @Operation(summary = "이미지 Emoji 상태 수정", description = "이미지 Emoji 상태를 수정한다. (For Sale <-> Paused 전환)" +
            "\n Live -> Paused, Paused -> Live", tags = "Studio Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> modifyStudioEmojiStatus(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                     @Valid @RequestBody ModifyStudioEmojiStatus req) {
//        CommonUtils.checkValidUser(req.getArtistId());

        req.setId(id);

        studioEmojiService.modifyStudioEmojiStatus(req);

        return ResponseEntity.ok().build();
    }

    /**
     * STUDIO 움직이는 이미지 Emoji 등록
     **/
    @Operation(summary = "움직이는 이미지 Emoji 등록 (Main File, Tab File, Emoji Files)", description = "움직이는 이미지 Emoji 를등록한다.", tags = "Studio Emoji API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/moving")
    public ResponseEntity<?> registerMovingEmoji(@Valid @RequestBody RegisterStudioMovingEmoji req) {
//        CommonUtils.checkValidUser(req.getArtistId());

        studioEmojiService.registerMovingEmoji(req);

        return ResponseEntity.ok().build();
    }
}
