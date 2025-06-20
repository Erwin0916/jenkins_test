package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles {
    LV1("001001") ,
    LV2("001002") ,
    LV3("001003") ,
    LV4("001004");

    private final String role;
}
