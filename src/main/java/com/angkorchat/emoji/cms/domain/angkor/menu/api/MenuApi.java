package com.angkorchat.emoji.cms.domain.angkor.menu.api;

import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.AddMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.ReorderMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.request.UpdateMenu;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.MenuDetail;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.MenuList;
import com.angkorchat.emoji.cms.domain.angkor.menu.dto.response.SimpleMenuList;
import com.angkorchat.emoji.cms.domain.angkor.menu.service.MenuService;
import com.angkorchat.emoji.cms.global.util.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Tag(name = "CMS Menu API", description = "CMS MENU API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("cms/menu")
public class MenuApi {

    private final MenuService menuService;

    @Operation( summary = "CMS Menu", description = "CMS Menu 정보를 조회한다.", tags = {"CMS Menu API"})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/privacy")
    public List<SimpleMenuList> menuList(@Parameter(name = "level", description = "권한 레벨 Major: Auth") @Pattern(regexp = "^\\d{6}$") @RequestParam(name = "level") String level) {
        return this.menuService.simpleMenuList(level);
    }

    @Operation(summary = "CMS 상위 메뉴 리스트", description = "CMS 의 상위 메뉴 목록을 조회한다", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/top/list")
    public List<MenuList> topMenuList() {
        return menuService.topMenuList();
    }

    @Operation(summary = "CMS 하위 메뉴 리스트", description = "선택한 CMS 상위 메뉴의 하위 메뉴 목록을 조회한다.", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{topId}/list")
    public List<MenuList> subMenuList(@Parameter(name = "topId", description = "상위 메뉴 id") @Min(value = 0) @PathVariable(name = "topId") Integer topId) {
        return menuService.subMenuList(topId);
    }

    @Operation(summary = "CMS 메뉴 정보", description = "선택한 CMS 메뉴의 상세 정보를 조회한다.", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MenuDetail menuDetail(@Parameter(name = "id", description = "메뉴 id") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return menuService.menuDetail(id);
    }

    @Operation(summary = "CMS 메뉴 추가", description = "상위 혹은 하위 메뉴를 추가한다." +
            "\n\n상위 메뉴 추가시 상위 메뉴 id 항목에는 0 을 입력한다.", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> addMenu(@Valid @RequestBody AddMenu req) {
        for(String level : req.getLevels()){
            ValidationUtils.patternValidator("^\\d{6}$", level, "levels");
        }

        menuService.addMenu(req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "CMS 메뉴 수정", description = "선택한 메뉴의 정보를 수정한다." +
            "\n\n파라미터 입력시 수정할 파라미터만 입력하여야 한다.", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMenu(@Parameter(name = "id", description = "메뉴 id") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                        @Valid @RequestBody UpdateMenu req) {
        for(String level : req.getLevels()){
            ValidationUtils.patternValidator("^\\d{6}$", level, "levels");
        }

        req.setId(id);
        menuService.updateMenu(req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "CMS 메뉴 순서 변경", description = "좌측에 나타날 메뉴의 정렬 순서를 변경한다.", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reorder")
    public ResponseEntity<?> reorderMenu(@Valid @RequestBody List<ReorderMenu> req) {
        menuService.reorderMenu(req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "CMS 메뉴 삭제", description = "선택한 메뉴를 삭제한다.", tags = "CMS Menu API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public ResponseEntity<?> deleteMenu(@RequestBody List<@Min(value = 1) Integer> req) {
        menuService.deleteMenu(req);

        return ResponseEntity.ok().build();
    }
}
