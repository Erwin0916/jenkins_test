package com.angkorchat.emoji.cms.domain.angkor.user.api;


import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.EmojiUserListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserUseEmojiListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserGiftedEmojiListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserPurchaseEmojiListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.service.UserService;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Size;

@Tag(name = "CMS User API", description = "앙코르 사용자 API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("cms/user")
public class UserApi {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserApi.class);

    /**
     * User List
     **/
    @Operation(summary = "이모지를 사용중인 User 리스트", description = "이모지를 사용중인 User 리스트를 조회한다.", tags = "CMS User API", parameters = {
            @Parameter(name = "searchKeyword", description = "검색어 : userAngkorId, phone")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/emoji/list")
    public Page<EmojiUserListDto> userEmojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                           @Size(max = 150) @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return userService.userEmojiList(pageable, searchKeyword);
    }

    /**
     * User Use List
     **/
    @Operation(summary = "User 가 보유한 이모지 리스트", description = "User 가 보유한 이모지 리스트를 조회한다.", tags = "CMS User API", parameters = {
            @Parameter(name = "angkorId", description = "angkorId")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/emoji/use/list")
    public Page<UserUseEmojiListDto> userUseEmojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                   @Size(max = 150) @RequestParam(name = "angkorId") String angkorId) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("b.id").descending());

        return userService.userUseEmojiList(pageable, angkorId);
    }

    /**
     * User Purchase List
     **/
    @Operation(summary = "User 가 구매한 이모지 리스트", description = "User 가 구매한 이모지 리스트를 조회한다.", tags = "CMS User API", parameters = {
            @Parameter(name = "angkorId", description = "angkorId")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/emoji/purchase/list")
    public Page<UserPurchaseEmojiListDto> userPurchaseEmojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                        @Size(max = 150) @RequestParam(name = "angkorId") String angkorId) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("b.id").descending());

        return userService.userPurchaseEmojiList(pageable, angkorId);
    }

    /**
     * User Gifted List
     **/
    @Operation(summary = "User 가 선물 받은 이모지 리스트", description = "User 가 선물 받은 이모지 리스트를 조회한다.", tags = "CMS User API", parameters = {
            @Parameter(name = "angkorId", description = "angkorId")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/emoji/gifted/list")
    public Page<UserGiftedEmojiListDto> userGiftedEmojiList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable,
                                                            @Size(max = 150) @RequestParam(name = "angkorId") String angkorId) {
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("b.id").descending());

        return userService.userGiftedEmojiList(pageable, angkorId);
    }


//
//    @Operation(summary = "게임즈 사용자 리스트", description = "게임즈 사용자 리스트를 조회한다.", tags = "User API", parameters = {
//            @Parameter(name = "pageable", description = "페이징 관련 변수(saveTm(가입 일자), accessTm)", required = true),
//            @Parameter(name = "searchKeyword", description = "검색어 (userId)"),
//            @Parameter(name = "status", description = "상태 (009001: Normal, 009002: Block, 009003: Withdraw)")
//    })
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/list")
//    public Page<UserList> selectUserList(@PageableDefault Pageable pageable,
//                                         @Size(max = 32) @RequestParam(required = false) String searchKeyword,
//                                         @RequestParam(required = false) String status) {
//        ValidationUtils.patternValidator("^\\d{6}$", status, "status");
//        pageable = CommonUtils.validSort(pageable, "saveTm", "accessTm");
//        return userService.userList(searchKeyword, status, pageable);
//    }
//
//    @Operation(summary = "게임즈 사용자 디테일", description = "게임즈 사용자 디테일을 조회한다.\n\n" +
//            "개인정보 제공 동의(7bit oper 2진수)\n\n" +
//            "1 이름(닉네임)\n\n" +
//            "10 프로필 사진\n\n" +
//            "100 전화번호\n\n" +
//            "1000 이메일\n\n" +
//            "10000 나이\n\n" +
//            "100000 생일\n\n" +
//            "1000000 성별", tags = "User API")
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/detail")
//    public UserDetail userDetail(@Parameter(name = "id", description = "사용자 id") @Min(value = 1) @PathVariable(name = "id") Integer id) {
//        return userService.userDetail(id);
//    }
//
//    /** 게임즈 사용자 게임 리스트 조회 **/
//    @Operation(summary = "게임즈 사용자 게임 플레이 목록", description = "게임즈 사용자가 플레이한 게임의 목록을 조회한다.", tags = "User API")
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/game/list")
//    public Page<UserPlayList> userPlayList(@Parameter(name = "pageable", description = "페이징 관련 변수") @PageableDefault Pageable pageable,
//                                           @Parameter(name = "id", description = "사용자 id") @Min(value = 1) @PathVariable(name = "id") Integer id) {
//        pageable = CommonUtils.validSort(pageable, "Unsorted");
//
//        return userService.userPlayList(id, pageable);
//    }
}
