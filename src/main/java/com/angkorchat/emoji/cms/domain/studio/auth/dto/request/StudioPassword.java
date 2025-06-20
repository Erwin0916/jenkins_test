package com.angkorchat.emoji.cms.domain.studio.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioPassword {
    @Schema(description = "암호화된 비밀번호", required = true, example = "6acf9f567e430fd85aea7743d460295952ef8125faa196773a2efbff72415c57")
    private String password;
}
