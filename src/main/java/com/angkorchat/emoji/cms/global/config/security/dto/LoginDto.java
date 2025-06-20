package com.angkorchat.emoji.cms.global.config.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private Integer id;

    private String adminName;

    private String auth;

    private String name;

    private PasswordDto password;

    private String userAngkorId;

    private String angkorId;

    private String loginId;

    public void resetTryCnt(String password) {
        this.password = new PasswordDto(password);
    }
}
