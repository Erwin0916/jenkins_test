package com.angkorchat.emoji.cms.domain.angkor.settlement.api;

import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.request.CmsArtistSettle;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response.CmsSettlementInfo;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response.CmsSettlementRequestList;
import com.angkorchat.emoji.cms.domain.angkor.settlement.service.CmsSettlementService;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CMS Settlement API", description = "CMS Settlement API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("cms/settlement")
public class CmsSettlementApi {
    private final CmsSettlementService cmsSettlementService;
    private static final Logger log = LoggerFactory.getLogger(CmsSettlementApi.class);


    /**
     * Studio Artist Settlement Info
     **/
    @Operation(summary = "Artist Settlement Request Detail", description = "Artist 의 정산 요청 정보 상세를 조회한다.", tags = "CMS Settlement API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/request/{id}")
    public CmsSettlementInfo cmsSettlementInfo(@Parameter(name = "id", description = "Settlement Request ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
//        CommonUtils.checkValidUser(id);
        return cmsSettlementService.cmsSettlementInfo(id);
    }

    /**
     * CMS Settlement Request List
     **/
    @Operation(summary = "Artist Settlement Request List", description = "Artist 의 정산 요청 정보를 조회한다.", tags = "CMS Settlement API", parameters = {
            @Parameter(name = "searchKeyword", description = "Artist Name 검색"),
            @Parameter(name = "status", description = "정산(입금) 여부(Y: 지급완료, N: 미지급)"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/request")
    public Page<CmsSettlementRequestList> cmsSettlementRequestList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                                   @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                                                   @RequestParam(name = "status", required = false) String status) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return cmsSettlementService.cmsSettlementRequestList(pageable, searchKeyword, status);
    }

    /**
     * CMS Artist Settle
     **/
    @Operation(summary = "Artist Settle", description = "Artist 수익을 정산한다.", tags = "CMS Settlement API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/request/{id}")
    public ResponseEntity<?> cmsArtistSettle(@Parameter(name = "id", description = "Request ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                @RequestBody CmsArtistSettle req) {
        req.setId(id);
        cmsSettlementService.cmsArtistSettle(req);

        return ResponseEntity.ok().build();
    }
}