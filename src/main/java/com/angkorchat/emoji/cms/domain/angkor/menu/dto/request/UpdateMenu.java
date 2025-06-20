package com.angkorchat.emoji.cms.domain.angkor.menu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UpdateMenu {
    @Schema(description = "메뉴 id", hidden = true)
    private Integer id;
    @Schema(description = "메뉴 이름")
    @Size(max = 32) @NotBlank
    private String menuName;
    @Schema(description = "메뉴 url")
    @Size(max = 255) @NotBlank
    private String menuLink;
    @Schema(description = "메뉴 폴더")
    @Size(max = 255) @NotBlank
    private String menuFolder;
    @Schema(description = "메뉴 파일")
    @Size(max = 50) @NotBlank
    private String menuFile;
    @Schema(description = "메뉴 icon")
    @Size(max = 16000)
    private String menuIcon;
    @Schema(description = "lv 권한 리스트 Major: Auth")
    private List<String> levels;
}