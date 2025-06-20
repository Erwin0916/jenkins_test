package com.angkorchat.emoji.cms.domain.angkor.admin.api;

import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.AdminGroupCode;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.AdminGroupDetail;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.AdminGroupList;
import com.angkorchat.emoji.cms.domain.angkor.admin.service.AdminService;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Tag(name = "CMS Admin Group API", description = "CMS 관리자 그룹 관리 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("cms/admin/group")
public class AdminGroupApi {
    private final AdminService adminService;

    /** 관리자 그룹 리스트 조회 **/
    @Operation(summary = "관리자 그룹 목록", description = "관리자 그룹 리스트를 조회한다.", tags = "CMS Admin Group API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Page<AdminGroupList> adminGroupList(@Parameter(name = "pageable", description = "pageable") @PageableDefault Pageable pageable,
                                               @Size(max = 32) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "unsorted");

        return adminService.adminGroupList(pageable, searchKeyword);
    }

    /** 관리자 그룹 상세 조회 **/
    @Operation(summary = "관리자 그룹 상세", description = "선택한 관리자 그룹의 상세 정보를 조회한다.", tags = "CMS Admin Group API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AdminGroupDetail adminGroupDetail(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer  id) {
        return adminService.adminGroupDetail(id);
    }

    /** 관리자 그룹 코드 리스트 **/
    @Operation(summary = "관리자 그룹 코드 리스트", description = "관리자 그룹 목록을 코드: 코드명 형식으로 가져온다.", tags = "CMS Admin Group API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/code")
    public List<AdminGroupCode> adminGroupCodeList() {
        return adminService.adminGroupCodeList();
    }

    /** 관리자 그룹 등록 **/
    @Operation(summary = "관리자 그룹 등록", description = "새로운 관리자 그룹을 등록한다.", tags = "CMS Admin Group API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> registerGroup(@Parameter(name = "name", description = "그룹 명") @Size(max = 32) @NotBlank @RequestParam(name = "name") String name) {
        adminService.registerGroup(name);

        return ResponseEntity.ok().build();
    }

    /** 관리자 그룹 수정 **/
    @Operation(summary = "관리자 그룹 수정", description = "선택한 관리자 그룹을 수정한다.", tags = "CMS Admin Group API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ResponseEntity<?> modifyGroup(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer  id,
                                         @Parameter(name = "name", description = "그룹 명") @Size(max = 32) @NotBlank @RequestParam(name = "name") String name) {
        adminService.modifyGroup(id, name);

        return ResponseEntity.ok().build();
    }

    /** 관리자 그룹 삭제 **/
    @Operation(summary = "관리자 그룹 삭제", description = "선택한 관리자 그룹을 삭제한다.", tags = "CMS Admin Group API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public ResponseEntity<?> deleteGroup(@RequestBody List<@Min(value = 1) Integer> ids) {
        adminService.deleteGroup(ids);

        return ResponseEntity.ok().build();
    }
}
