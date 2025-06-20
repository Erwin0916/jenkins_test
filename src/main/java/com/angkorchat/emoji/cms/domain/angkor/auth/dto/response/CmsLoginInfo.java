package com.angkorchat.emoji.cms.domain.angkor.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsLoginInfo {
    @Schema(description = "관리자 이름", required = true, example = "james")
    private String name;
    @Schema(description = "이메일", required = true, example = "example@email.com")
    private String email;
    @Schema(description = "전화번호", required = true, example = "01011119966")
    private String phoneNumber;
    @Schema(description = "앙코르아이디", required = true, example = "ak213k312")
    private String angkorId;
    @Schema(description = "연동된 앙코르라이프 userAngkorId", example = "user A")
    private String userAngkorId;
    @Schema(description = "권한 major : 001 (Auth)", required = true, example = "001001")
    private String permission;
    @Schema(description = "그룹명", example = "Group A")
    private String groupName;
}
