package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import com.angkorchat.emoji.cms.domain.angkor.user.dto.UserComment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDto extends UserListDto {
    @Schema(description = "개인정보 제공 동의 10진수")
    private Integer privacyConsent;
    @Schema(description = "유저 코멘트")
    private List<UserComment> userCommentList;
}