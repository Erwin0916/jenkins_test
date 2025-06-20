package com.angkorchat.emoji.cms.domain.angkor.menu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SimpleMenuList {
    @Schema(description = "메뉴 id")
    private Integer id;
    @Schema(description = "상위 메뉴 id")
    private Integer parentId;
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
    @Schema(description = "메뉴 깊이")
    private Integer depth;
    @Schema(description = "하위 메뉴")
    private List<SimpleMenuList> children = new ArrayList<>();

    public void addChildren(SimpleMenuList child) {
        this.children.add(child);
    }
}