package com.angkorchat.emoji.cms.domain.angkor.menu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class ReorderMenu {
    @Schema(description = "메뉴 id list")
    @Min(value = 1) @NotNull
    private Integer id;
    @Schema(description = "정렬 순서")
    @Range(min = 0, max = 127) @NotNull
    private Integer displayOrder;
}