package com.angkorchat.emoji.cms.domain.angkor.menu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDetail {
    @Schema(description = "메뉴 id")
    private Integer id;
    @Schema(description = "메뉴 이름")
    private String menuName;
    @Schema(description = "메뉴 url")
    private String menuLink;
    @Schema(description = "메뉴 폴더")
    private String menuFolder;
    @Schema(description = "메뉴 파일")
    private String menuFile;
    @Schema(description = "메뉴 icon")
    private String menuIcon;
    @Schema(description = "정렬 순서")
    private Integer displayOrder;
    @Schema(description = "lv1 권한 사용 여부")
    private String lv1;
    @Schema(description = "lv2 권한 사용 여부")
    private String lv2;
    @Schema(description = "lv3 권한 사용 여부")
    private String lv3;
    @Schema(description = "lv4 권한 사용 여부")
    private String lv4;
    @Schema(description = "권한 레벨 코드")
    private List<String> levels;
}