package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPlayList {
    @Schema(description = "게임 아이디")
    private Integer gameId;
    @Schema(description = "사용자 유저 앙코르 아이디")
    private String userAngkorId;
    @Schema(description = "사용자 상태")
    private String status;
    @Schema(description = "게임 이름")
    private String name;
    @Schema(description = "게임 메인 카테고리 (major: Main Game Category")
    private String mainCategory;
    @Schema(description = "게임 서브 카테고리 (major: Sub Game Category")
    private String subCategory;
    @Schema(description = "게임 썸네일 url")
    private String thumbUrl;
}
