package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String angkorId;
    private String userId;
    private String userAngkorId;
    private String ScreenName;
    private String email;
    private String phone;
    private String phoneCode;
    private String phoneNumber;
    private String profileImage;
    private String status;
    private String language;
}