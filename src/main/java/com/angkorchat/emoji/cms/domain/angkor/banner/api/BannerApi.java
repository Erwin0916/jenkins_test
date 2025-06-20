package com.angkorchat.emoji.cms.domain.angkor.banner.api;

import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ModifyBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.RegisterBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.ReorderBanner;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.request.UpdateBannerStatus;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerDetail;
import com.angkorchat.emoji.cms.domain.angkor.banner.dto.response.BannerList;
import com.angkorchat.emoji.cms.domain.angkor.banner.service.BannerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "CMS Banner API", description = "배너 관리 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("cms/banner")
public class BannerApi {
    private final BannerService bannerService;

    /**
     * Emoji Shop 배너 리스트 조회
     **/
    @Operation(summary = "Emoji Shop 배너 목록", description = "Emoji Shop 배너 리스트를 조회한다.", tags = "CMS Banner API", parameters = {
            @Parameter(name = "searchKeyword", description = "검색어 (name)")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<BannerList> bannerList(@Parameter(name = "status", description = "상태 (등록 : N, 라이브 : Y)") @Size(max = 1) @RequestParam(name = "status", required = false) String status,
                                       @Parameter(name = "searchKeyword", description = "name") @Size(max = 64) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
//        pageable = CommonUtils.validSort(pageable, "Unsorted");

        return bannerService.bannerList(status, searchKeyword);
    }

    /**
     * Banner Detail
     **/
    @Operation(summary = "Banner Detail", description = "Banner Detail 을 조회한다.", tags = "CMS Banner API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BannerDetail bannerDetail(@Parameter(name = "id", description = "Banner ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return bannerService.bannerDetail(id);
    }

    /**
     * Emoji Shop 배너 등록
     **/
    @Operation(summary = "Emoji Shop 배너 등록", description = "Emoji Shop 배너 를 등록한다.", tags = "CMS Banner API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerBanner(@Valid @RequestPart(name = "req") RegisterBanner req, @RequestPart(name = "imageFile") MultipartFile imageFile) {
        req.setImageFile(imageFile);
        bannerService.registerBanner(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop 배너 수정
     **/
    @Operation(summary = "Emoji Shop 배너 수정", description = "Emoji Shop 배너 를 수정한다. (이미지 파일 수정시에만 imageFile 전송)", tags = "CMS Banner API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> modifyBanner(@Parameter(name = "id", description = "Banner ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                          @Valid @RequestPart(name = "req") ModifyBanner req, @RequestPart(name = "imageFile", required = false) MultipartFile imageFile) {
        req.setImageFile(imageFile);
        req.setId(id);
        bannerService.modifyBanner(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop 배너 노출 순서 변경
     **/
    @Operation(summary = "Emoji Shop 배너 노출 순서 변경 ", description = "Emoji Shop 배너 노출 순서를 변경한다.", tags = "CMS Banner API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reorder")
    public ResponseEntity<?> reorderBanner(@Valid @RequestBody List<ReorderBanner> req) {
        bannerService.reorderBanner(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop 배너 상태 변경
     **/
    @Operation(summary = "Emoji Shop 배너 상태 변경", description = "Emoji Shop 배너 상태를 변경한다.", tags = "CMS Banner API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateBannerStatus(@Parameter(name = "id", description = "Banner ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                @Valid @RequestBody UpdateBannerStatus req) {
        req.setId(id);
        bannerService.updateBannerStatus(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop 배너 삭제
     **/
    @Operation(summary = "Emoji Shop 배너 삭제", description = "Emoji Shop 배너를 삭제한다.", tags = "CMS Banner API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBanner(@Parameter(name = "id", description = "Emoji ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        bannerService.deleteBanner(id);

        return ResponseEntity.ok().build();
    }
}