package com.angkorchat.emoji.cms.domain.angkor.artist.api;

import com.angkorchat.emoji.cms.domain.angkor.artist.dto.request.ModifyArtistAccount;
import com.angkorchat.emoji.cms.domain.angkor.artist.dto.request.RegisterArtistAccount;
import com.angkorchat.emoji.cms.domain.angkor.artist.dto.response.*;
import com.angkorchat.emoji.cms.domain.angkor.artist.service.CmsArtistService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CMS Artist API", description = "Emoji Artist 관리 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("cms/artist")
public class CmsArtistApi {
    private final CmsArtistService cmsArtistService;

    /**
     * Emoji Artist List
     **/
    @Operation(summary = "Emoji Artist 리스트", description = "Emoji Artist 리스트를 조회한다.", tags = "CMS Artist API", parameters = {
            @Parameter(name = "status", description = "상태 타입(1 : Normal, 2: Blocked, 3: Deleted)"),
            @Parameter(name = "searchKeyword", description = "검색어 : emojiName, artistName")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Page<ArtistListDto> emojiArtistList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                               @Size(max = 150) @RequestParam(name = "status", required = false) String status,
                                               @Size(max = 150) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return cmsArtistService.emojiArtistList(pageable, status, searchKeyword);
    }

    /**
     * Emoji Artist Detail
     **/
    @Operation(summary = "Emoji Artist Detail", description = "Emoji Artist Detail 을 조회한다.", tags = "CMS Artist API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ArtistDetail emojiArtistDetail(@Parameter(name = "id", description = "Emoji ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return cmsArtistService.emojiArtistDetail(id);
    }

    /**
     * Artist Emoji List
     **/
    @Operation(summary = "Artist Emoji 리스트", description = "Artist 의 Emoji 리스트를 조회한다. (상세 조회 시 GET /emoji/request/{id} 사용)", tags = "CMS Artist API", parameters = {
            @Parameter(name = "status", description = "상태 타입(major: 002, minor: 001(For Sale), 002(Paused), 003(Deleted), 004(In Review), 005(Rejected)"),
            @Parameter(name = "searchKeyword", description = "검색어 : emojiName")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/emoji/list")
    public Page<ArtistEmojiListDto> emojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                              @Parameter(name = "id", description = "Artist ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                              @Size(max = 150) @RequestParam(name = "status", required = false) String status,
                                              @Size(max = 150) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return cmsArtistService.artistEmojiList(pageable, id, status, searchKeyword);
    }

    /**
     * CMS Artist Account Info
     **/
    @Operation(summary = "CMS Artist Account Info", description = "Artist 의 Account 정보를 조회한다.", tags = "CMS Artist API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/account/info")
    public ArtistAccountInfo cmsArtistAccountInfo(@Parameter(name = "id", description = "Artist ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return cmsArtistService.cmsArtistAccountInfo(id);
    }

    /**
     * CMS Artist Settlement Request List
     **/
    @Operation(summary = "CMS  Artist Settlement Request Info", description = "Artist 의 정산 요청 정보를 조회한다.", tags = "CMS Artist API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/request")
    public Page<ArtistSettlementRequestList> cmsArtistSettlementRequestList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                                            @Parameter(name = "id", description = "Artist ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return cmsArtistService.cmsArtistSettlementRequestList(pageable, id);
    }

    /**
     * Register Artist Account
     **/
    @Operation(summary = "Artist 계좌 생성", description = "Artist 의 Account 를 생성한다.", tags = "CMS Artist API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/account")
    public ResponseEntity<?> registerArtistAccount(@Parameter(name = "id", description = "Artist ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                   @Valid @RequestBody RegisterArtistAccount req) {
        req.setArtistId(id);
        cmsArtistService.registerArtistAccount(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Register Artist Account
     **/
    @Operation(summary = "Artist 계좌 정보 수정", description = "Artist 의 은행 계좌 정보를 수정한다.", tags = "CMS Artist API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/account")
    public ResponseEntity<?> modifyArtistAccount(@Parameter(name = "id", description = "Artist ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                 @Valid @RequestBody ModifyArtistAccount req) {
        req.setArtistId(id);
        cmsArtistService.modifyArtistAccount(req);

        return ResponseEntity.ok().build();
    }
}
