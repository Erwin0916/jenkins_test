package com.angkorchat.emoji.cms.domain.angkor.category.api;

import com.angkorchat.emoji.cms.domain.angkor.category.dto.request.*;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryDetail;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryEmojiList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryList;
import com.angkorchat.emoji.cms.domain.angkor.category.dto.response.CategoryRegisteredEmojiList;
import com.angkorchat.emoji.cms.domain.angkor.category.service.CategoryService;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CMS Category API", description = "카테고리 관리 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("cms/category")
public class CategoryApi {
    private final CategoryService categoryService;

    /**
     * Emoji Shop 메인 카테고리 리스트 조회
     **/
    @Operation(summary = "Emoji Shop 메인 카테고리 목록", description = "Emoji Shop  메인 카테고리 리스트를 조회한다.", tags = "CMS Category API", parameters = {
            @Parameter(name = "searchKeyword", description = "검색어 (name)")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<CategoryList> categoryList(@Parameter(name = "status", description = "상태 (등록 : N, 라이브 : Y)") @Size(max = 1) @RequestParam(name = "status", required = false) String status,
                                           @Parameter(name = "categoryType", description = "(1: MD Pick, 2:HashTag)") @Min(value = 1) @RequestParam(name = "categoryType", required = false) Integer categoryType,
                                           @Parameter(name = "searchKeyword", description = "name") @Size(max = 64) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {

        return categoryService.categoryList(status, categoryType, searchKeyword);
    }

    /**
     * 메인 카테고리 Detail
     **/
    @Operation(summary = "Emoji Shop 메인 카테고리 Detail", description = "Emoji Shop 메인 카테고리 Detail 을 조회한다.", tags = "CMS Category API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CategoryDetail categoryDetail(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        return categoryService.categoryDetail(id);
    }

    /**
     * Emoji Shop 메인 카테고리 등록
     **/
    @Operation(summary = "Emoji Shop 메인 카테고리 등록", description = "Emoji Shop 메인 카테고리를 등록한다. 카테고리 타입(1: MD Pick, 2:HashTag)", tags = "CMS Category API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> registerCategory(@Valid @RequestBody RegisterCategory req) {
        categoryService.registerCategory(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop 메인 카테고리 수정
     **/
    @Operation(summary = "Emoji Shop 메인 카테고리 수정", description = "Emoji Shop 메인 카테고리를 수정한다.", tags = "CMS Category API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> modifyCategory(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                            @Valid @RequestBody ModifyCategory req) {
        req.setId(id);
        categoryService.modifyCategory(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop Category 노출 순서 변경
     **/
    @Operation(summary = "Emoji Shop Category 노출 순서 변경 ", description = "Emoji Shop Category 노출 순서를 변경한다. ", tags = "CMS Category API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reorder")
    public ResponseEntity<?> reorderCategory(@Valid @RequestBody List<ReorderCategory> req) {
        categoryService.reorderCategory(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop Category 상태 변경
     **/
    @Operation(summary = "Emoji Shop Category 상태(status) 변경", description = "Emoji Shop 배너 상태를 변경한다.", tags = "CMS Category API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateCategoryStatus(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                @Valid @RequestBody UpdateCategoryStatus req) {
        req.setId(id);
        categoryService.updateCategoryStatus(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Emoji Shop 메인 카테고리 삭제
     **/
    @Operation(summary = "Emoji Shop 메인 카테고리 삭제", description = "Emoji Shop 메인 카테고리를 삭제한다.", tags = "CMS Category API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.ok().build();
    }

    /**
     * Category 에 등록된 Emoji 리스트 조회
     **/
    @Operation(summary = "Category 에 등록된 Emoji 리스트 조회", description = "Category 에 등록된 Emoji 리스트를 조회한다.", tags = "CMS Category API", parameters = {
            @Parameter(name = "id", description = "Category ID")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/emoji/list")
    public List<CategoryRegisteredEmojiList> categoryEmojiList(@Min(value = 1) @PathVariable(name = "id") Integer id) {
        return categoryService.categoryEmojiList(id);
    }

    /**
     * Category 에 미등록된 Emoji 리스트 조회
     **/
    @Operation(summary = "Category 에 미등록된 Emoji 리스트 조회", description = "Category 에 미등록된 Emoji 리스트를 조회한다.", tags = "CMS Category API", parameters = {
            @Parameter(name = "id", description = "Category ID")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/emoji/unregistered")
    public Page<CategoryEmojiList> categoryEmojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                     @Min(value = 1) @PathVariable(name = "id") Integer id) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");

        return categoryService.categoryUnregisteredEmojiList(pageable, id);
    }

    /**
     * 카테고리에 Emoji Set 등록
     **/
    @Operation(summary = "카테고리에 Emoji Set 등록", description = "카테고리에 Emoji Set 을 등록한다.(categoryName 과 같은 tag 를 갖고있는 Emoji Set 만 등록한다.)", tags = "CMS Category API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/emoji")
    public ResponseEntity<?> registerEmojiToCategory(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                     @Valid @RequestBody RegisterEmojiToCategory req) {
        req.setCategoryId(id);
        categoryService.registerEmojiToCategory(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Category Emoji 노출 순서 변경
     **/
    @Operation(summary = "Category 에 등록된 Emoji 노출 순서 변경", description = "Category 에 등록된 Emoji 노출 순서를 변경한다.", tags = "CMS Category API", parameters = {
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/emoji/reorder")
    public ResponseEntity<?> reorderCategoryEmoji(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                  @Valid @RequestBody List<ReorderCategoryEmoji> req) {
        categoryService.reorderCategoryEmoji(id, req);

        return ResponseEntity.ok().build();
    }

    /**
     * 카테고리에서 Emoji Set 삭제
     **/
    @Operation(summary = "카테고리에서 Emoji Set 삭제", description = "카테고리에서 Emoji Set 을 삭제한다.", tags = "CMS Category API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/emoji")
    public ResponseEntity<?> deleteEmojiFromCategory(@Parameter(name = "id", description = "Category ID") @Min(value = 1) @PathVariable(name = "id") Integer id,
                                                     @Parameter(name = "emojiIds", description = "Emoji IDs") @RequestParam(name = "emojiIds") List<@Min(value = 1) Integer> emojiIds) {
        categoryService.deleteEmojiFromCategory(id, emojiIds);

        return ResponseEntity.ok().build();
    }
}