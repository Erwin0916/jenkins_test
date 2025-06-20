package com.angkorchat.emoji.cms.domain.angkor.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminList {
    @Schema(description = "등록 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer id;
    @Schema(description = "관리자 Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "Admin2")
    private String adminId;
    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String name;
    @Schema(description = "권한 (major: Auth)", requiredMode = Schema.RequiredMode.REQUIRED, example = "001001")
    private String auth;
    @Schema(description = "핸드폰", requiredMode = Schema.RequiredMode.REQUIRED, example = "010-1111-6221")
    private String phone;
    @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@email.com")
    private String email;
    @Schema(description = "그룹", requiredMode = Schema.RequiredMode.REQUIRED, example = "group Name")
    private String groupName;
}
