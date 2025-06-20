package com.angkorchat.emoji.cms.domain.angkor.user.service;

import com.angkorchat.emoji.cms.domain.angkor.user.dao.mapper.UserQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.*;
import com.angkorchat.emoji.cms.global.config.security.service.UserAes256Service;
import com.angkorchat.emoji.cms.global.error.BaseException;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserQueryMapper userQueryMapper;
    private final UserAes256Service userAes256Service;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${chatCmsApi.url}")
    private String chatCmsApiUrl;

    public Page<EmojiUserListDto> userEmojiList(Pageable pageable, String searchKeyword) {
        String url;
        HashMap requestMap = new HashMap();

        List<String> angkorIdList = new ArrayList<>();
        List<EmojiUserListDto> emojiUserList = new ArrayList<>();
        // searchKeyword
        if(searchKeyword == null || searchKeyword.isBlank()) {
            // searchKeyword 없을때
            emojiUserList = userQueryMapper.userList(pageable, angkorIdList);

            for (EmojiUserListDto emojiUser: emojiUserList){
                angkorIdList.add(emojiUser.getAngkorId());
            }
            requestMap.put("angkorIdList", angkorIdList);

            // Cms API 호출 (/users/info/user)
            url = chatCmsApiUrl + "/users/info/user";

            List<UserListDto> userList = CommonUtils.callApi(url, HttpMethod.POST, null, requestMap, null, new ParameterizedTypeReference<List<UserListDto>>() {});

            for (EmojiUserListDto emojiUser: emojiUserList){
                for(UserListDto userDto : userList) {
                    if(emojiUser.getAngkorId().equals(userDto.getAngkorId())) {
                        try {
                            if(userDto.getUserAngkorId() != null && !userDto.getUserAngkorId().equals("")) {
                                emojiUser.setUserAngkorId(userAes256Service.decrypt(userDto.getUserAngkorId()));
                            } else {
                                emojiUser.setUserAngkorId("");
                            }
                            if(userDto.getScreenName() != null && !userDto.getScreenName().equals("")) {
                                emojiUser.setScreenName(userAes256Service.decrypt(userDto.getScreenName()));
                            } else {
                                emojiUser.setScreenName("");
                            }
                            if(userDto.getPhone() != null && !userDto.getPhone().equals("")) {
                                emojiUser.setPhone(userAes256Service.decrypt(userDto.getPhone()));
                            } else {
                                emojiUser.setPhone("");
                            }
                            if(userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().equals("")) {
                                emojiUser.setPhoneNumber(userAes256Service.decrypt(userDto.getPhoneNumber()));
                            } else {
                                emojiUser.setPhoneNumber("");
                            }
                        } catch (Exception e) {
                            log.error("User Info Decrypt Exception");
                            throw new BaseException();
                        }
                    }
                }
            }

        } else {
            // searchKeyword 있을때
            // Cms API 호출 (/users/info/user/{id})
            url = chatCmsApiUrl + "/users/info/user/" + searchKeyword;
            List<UserListDto> userList = CommonUtils.callApi(url, HttpMethod.GET, null, requestMap, null, new ParameterizedTypeReference<List<UserListDto>>() {});

            if(!userList.isEmpty()) {
                for (UserListDto userDto: userList){
                    angkorIdList.add(userDto.getAngkorId());
                }

                // 받아온 값으로 List 를 가져온다.
                emojiUserList = userQueryMapper.userList(pageable, angkorIdList);

                for (EmojiUserListDto emojiUser: emojiUserList){
                    for(UserListDto userDto : userList) {
                        if(emojiUser.getAngkorId().equals(userDto.getAngkorId())) {
                            try {
                                if(userDto.getUserAngkorId() != null && !userDto.getUserAngkorId().equals("")) {
                                    emojiUser.setUserAngkorId(userAes256Service.decrypt(userDto.getUserAngkorId()));
                                } else {
                                    emojiUser.setUserAngkorId("");
                                }
                                if(userDto.getScreenName() != null && !userDto.getScreenName().equals("")) {
                                    emojiUser.setScreenName(userAes256Service.decrypt(userDto.getScreenName()));
                                } else {
                                    emojiUser.setScreenName("");
                                }
                                if(userDto.getPhone() != null && !userDto.getPhone().equals("")) {
                                    emojiUser.setPhone(userAes256Service.decrypt(userDto.getPhone()));
                                } else {
                                    emojiUser.setPhone("");
                                }
                                if(userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().equals("")) {
                                    emojiUser.setPhoneNumber(userAes256Service.decrypt(userDto.getPhoneNumber()));
                                } else {
                                    emojiUser.setPhoneNumber("");
                                }
                            } catch (Exception e) {
                                log.error("User Info Decrypt Exception");
                                throw new BaseException();
                            }
                        }
                    }
                }
            }
        }

        return new PageImpl<>(emojiUserList,
                pageable,
                userQueryMapper.userListCount(angkorIdList));
    }

    public Page<UserUseEmojiListDto> userUseEmojiList(Pageable pageable, String angkorId) {
        return new PageImpl<>(userQueryMapper.userUseEmojiList(pageable, angkorId),
                pageable,
                userQueryMapper.userUseEmojiListCount(angkorId));
    }

    public Page<UserPurchaseEmojiListDto> userPurchaseEmojiList(Pageable pageable, String angkorId) {
        return new PageImpl<>(userQueryMapper.userPurchaseEmojiList(pageable, angkorId),
                pageable,
                userQueryMapper.userPurchaseEmojiListCount(angkorId));
    }

    public Page<UserGiftedEmojiListDto> userGiftedEmojiList(Pageable pageable, String angkorId) {
        return new PageImpl<>(userQueryMapper.userGiftedEmojiList(pageable, angkorId),
                pageable,
                userQueryMapper.userGiftedEmojiListCount(angkorId));
    }

//    public Page<UserList> userList(String searchKeyword, String status, Pageable pageable) {
//       List<UserList> userList = userQueryMapper.userList(pageable, searchKeyword, status);
//
//        for(UserList user: userList) {
//            try {
//                if(user.getUserAngkorId() != null) {
//                    user.setUserAngkorId(userAes256Service.decrypt(user.getUserAngkorId()));
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("userList Decrypt Error");
//            }
//        }
//
//        return new PageImpl<>(userList,
//                pageable,
//                userQueryMapper.userListCount(searchKeyword, status));
//    }
//
//    public UserDetail userDetail(Integer id) {
//
//        UserDetail userDetail = userQueryMapper.userDetail(id);
//
//        try {
//            if(userDetail.getUserAngkorId() != null) {
//                userDetail.setUserAngkorId(userAes256Service.decrypt(userDetail.getUserAngkorId()));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("userDetail Decrypt Error");
//        }
//
//        return userDetail;
//    }
//
//    public Page<UserPlayList> userPlayList(Integer id, Pageable pageable) {
//        List<UserPlayList> userPlayList = userQueryMapper.userPlayList(pageable, id);
//
//        for(UserPlayList user: userPlayList) {
//            try {
//                if(user.getUserAngkorId() != null) {
//                    user.setUserAngkorId(userAes256Service.decrypt(user.getUserAngkorId()));
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("userPlayList Decrypt Error");
//            }
//        }
//
//        return new PageImpl<>(userPlayList,
//                pageable,
//                userQueryMapper.userPlayListCount(id));
//    }
}