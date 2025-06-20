package com.angkorchat.emoji.cms.domain.angkor.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserComment{

    @Schema(description = "어드민 아이디")
    private String adminId;
    @Schema(description = "가입 날짜")
    private String cmtSaveTm;
    @Schema(description = "코멘트")
    private String cmt;
}