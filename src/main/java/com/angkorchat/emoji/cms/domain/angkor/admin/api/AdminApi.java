package com.angkorchat.emoji.cms.domain.angkor.admin.api;

import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.ModifyAdminInfo;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.RegisterAdmin;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.request.ResetPassword;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.AdminDetail;
import com.angkorchat.emoji.cms.domain.angkor.admin.dto.response.AdminList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;

@Tag(name = "CMS Admin API", description = "CMS 관리자 관리 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("cms/admin")
public class AdminApi {
    private final AdminService adminService;

    /** 관리자 리스트 조회 **/
    @Operation(summary = "관리자 목록", description = "관리자 리스트를 조회한다.", tags = "CMS Admin API", parameters = {
            @Parameter(name = "searchKeyword", description = "검색어 (name, email, phone)")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Page<AdminList> adminList(@Parameter(name = "pageable", description = "pageable") @PageableDefault Pageable pageable,
                                     @Size(max =64) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");

        return adminService.adminList(pageable, searchKeyword);
    }

    /** 관리자 상세 조회 **/
    @Operation(summary = "상세 조회", description = "관리자의 상세 정보를 조회한다.", tags = "CMS Admin API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AdminDetail adminDetail(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return adminService.adminDetail(id);
    }

    /** 관리자 생성 **/
    @Operation(summary = "관리자 등록", description = "새로운 관리자 아이디를 생성한다.", tags = "CMS Admin API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterAdmin req) {
        adminService.registerAdmin(req);

        return ResponseEntity.ok().build();
    }

    /** 관리자 수정 **/
    @Transactional
    @Operation(summary = "관리자 정보 수정", description = "선택한 관리자의 정보를 수정한다.\n\n" +
            "수정하고자 하는 정보만 입력한다.", tags = "CMS Admin API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ResponseEntity<?> modifyAdmin(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                         @Valid @RequestBody ModifyAdminInfo req) {
        adminService.modifyAdminInfo(id, req);

        return ResponseEntity.ok().build();
    }

    /** 관리자 그룹 수정 **/
    @Operation(summary = "관리자 소속 그룹 수정", description = "선택한 관리자의 소속 그룹을 수정한다.", tags = "CMS Admin API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/group")
    public ResponseEntity<?> modifyAdminGroup(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                              @Parameter(name = "groupId", description = "그룹 id", required = true) @Min(value = 1) @RequestParam Integer groupId) {
        adminService.modifyAdminGroup(id, groupId);

        return ResponseEntity.ok().build();
    }

    /** 관리자 삭제 **/
    @Operation(summary = "관리자 삭제", description = "선택한 관리자 아이디를 삭제한다.", tags = "CMS Admin API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public ResponseEntity<?> deleteAdmin(@RequestBody List<@Min(value = 1) Integer> ids) {
        adminService.deleteAdmin(ids);

        return ResponseEntity.ok().build();
    }

    /** 관리자 비밀번호 초기화 **/
    @Operation(summary = "비밀번호 초기화", description = "선택한 관리자의 비밀번호를 초기화한다.", tags = "CMS Admin API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> resetAdminPassword(@Parameter(name = "id", description = "등록 번호") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                @RequestBody ResetPassword req) {
        adminService.resetAdminPassword(id, req);

        return ResponseEntity.ok().build();
    }
}
