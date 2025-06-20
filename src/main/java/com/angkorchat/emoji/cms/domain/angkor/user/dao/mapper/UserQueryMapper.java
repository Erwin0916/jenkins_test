package com.angkorchat.emoji.cms.domain.angkor.user.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.EmojiUserListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserUseEmojiListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserGiftedEmojiListDto;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserPurchaseEmojiListDto;
import org.apache.ibatis.annotations.Param;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserQueryMapper {

    List<EmojiUserListDto> userList(@Param("pageable") Pageable pageable, @Param("angkorIdList") List<String> angkorIdList);
    int userListCount(@Param("angkorIdList") List<String> angkorIdList);

    List<UserUseEmojiListDto> userUseEmojiList(@Param("pageable") Pageable pageable, @Param("angkorId") String angkorId);
    int userUseEmojiListCount(@Param("angkorId") String angkorId);

    List<UserPurchaseEmojiListDto> userPurchaseEmojiList(@Param("pageable") Pageable pageable, @Param("angkorId") String angkorId);
    int userPurchaseEmojiListCount(@Param("angkorId") String angkorId);

    List<UserGiftedEmojiListDto> userGiftedEmojiList(@Param("pageable") Pageable pageable, @Param("angkorId") String angkorId);
    int userGiftedEmojiListCount(@Param("angkorId") String angkorId);


//    List<UserList> userList(@Param("pageable") Pageable pageable,
//                            @Param("searchKeyword") String searchKeyword,
//                            @Param("status") String status);
//
//    long userListCount(@Param("searchKeyword") String searchKeyword,
//                       @Param("status") String status);
//
//    UserDetail userDetail(@Param("id") Integer id);
//
//    List<UserPlayList> userPlayList(@Param("pageable") Pageable pageable,
//                                    @Param("id") Integer id);
//
//    long userPlayListCount(Integer id);
//
//    void updateUserGameState(@Param("id") Integer id,
//                             @Param("gameId") Integer gameId,
//                             @Param("state") String state);
//
//    List<String> angkorChatUserInfo(@Param("userIds") List<String> userIds);
}
