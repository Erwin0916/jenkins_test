package com.angkorchat.emoji.cms.domain.angkor.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminGroupCode {
    @Schema(description = "그룹 id")
    private Integer id;
    @Schema(description = "그룹 명")
    private String name;
}
