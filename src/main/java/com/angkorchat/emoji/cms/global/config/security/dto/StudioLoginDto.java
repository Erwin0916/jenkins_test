package com.angkorchat.emoji.cms.global.config.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioLoginDto {
    private Integer id;
    private String angkorId;
    private String name;
    private String phoneNumber;
    private String phoneCode;
    private String email;
    private String status;
}
