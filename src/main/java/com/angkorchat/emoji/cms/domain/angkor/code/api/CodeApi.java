package com.angkorchat.emoji.cms.domain.angkor.code.api;

import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.CodeList;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.MajorList;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.response.SimpleCode;
import com.angkorchat.emoji.cms.domain.angkor.code.service.CodeService;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.AddCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.UpdateCode;
import com.angkorchat.emoji.cms.domain.angkor.code.dto.request.UpdateMajorName;
import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import com.angkorchat.emoji.cms.global.util.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Tag(name = "CMS Code API", description = "공통 코드 관리 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("cms/code")
public class CodeApi {
    private final CodeService codeService;

    /** 메이저 코드 목록 **/
    @Operation(summary = "메이저 코드 목록", description = "공통 코드의 메이저 목록을 조회한다.", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/major/list")
    public Page<MajorList> majorList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                     @Size(max = 128) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");

        return codeService.majorList(pageable, searchKeyword);
    }

    /** 하위 코드 목록 **/
    @Operation(summary = "하위 코드 목록", description = "선택한 메이저 코드의 하위 코드 목록을 조회한다.", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{major}/list")
    public Page<CodeList> codeList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                   @Size(max = 64) @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                   @Pattern(regexp = "^\\d{3}$") @Min(value = 1) @PathVariable(name = "major") String major) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");

        return codeService.codeList(pageable, searchKeyword, major);
    }

    /** 심플 코드 목록 **/
    @Operation(summary = "간단 코드 목록", description = "여러 상황에서 사용할 수 있는 메이저 별 하위코드 목록을 조회한다.", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{major}/list/simple")
    public List<SimpleCode> simpleCodeList(@Parameter(name = "major", description = "메이저 코드")
                                           @Pattern(regexp = "^\\d{3}$") @Min(value = 1) @PathVariable(name = "major") String major) {
        return codeService.simpleCodeList(major);
    }

    @Operation(summary = "코드 생성", description = "메이저 코드를 선택하여 해당 메이저 코드에 새로운 하위 코드를 생성한다." +
            "\n\n메이저 코드를 선택한 경우에는 메이저 코드 명을 입력하지 않는다." +
            "\n\n새로운 메이저 코드를 생성하고 싶은 경우에는 메이저 코드를 선택하지 않고 메이저 코드 명 만을 입력하여 생성한다." +
            "\n\n새로운 코드 생성 예시: majorCode + codeName" +
            "\n\n새로운 메이저 코드 생성 예시: majorName + codeName", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> addCode(@Valid @RequestBody AddCode req) {
        if (StringUtils.hasText(req.getMajor())) {
            // major 가 있는 경우 신규 코드 추가
            ValidationUtils.patternValidator("^\\d{3}$", req.getMajor(), "major");

            codeService.addCode(req);
        } else {
            // major 가 없는 경우 신규 major 및 코드 추가
            if(!StringUtils.hasText(req.getMajorName())) {
                throw new InvalidInputDataFormatException("majorName");
            }

            codeService.addMajor(req);
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "코드 수정", description = "선택한 코드를 수정한다.", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{code}")
    public ResponseEntity<?> updateCode(@Parameter(name = "code", description = "공통 코드") @Pattern(regexp = "^\\d{6}$") @PathVariable(name = "code") String code,
                                        @Valid @RequestBody UpdateCode req) {
        req.setCode(code);
        codeService.updateCode(req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "메이저 코드 이름 수정", description = "선택한 메이저 코드의 이름을 수정한다.", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{major}/name")
    public ResponseEntity<?> updateMajorName(@Parameter(name = "major", description = "메이저 코드")
                                             @Pattern(regexp = "^\\d{3}$") @Min(value = 1) @PathVariable(name = "major") String major,
                                             @Valid @RequestBody UpdateMajorName req) {
        req.setMajor(major);
        codeService.updateMajorName(req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "코드 삭제", description = "선택한 코드를 삭제한다.", tags = "CMS Code API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteCode(@Parameter(name = "code", description = "공통 코드") @Pattern(regexp = "^\\d{6}$") @PathVariable(name = "code") String code) {
        codeService.deleteCode(code);

        return ResponseEntity.ok().build();
    }
}
