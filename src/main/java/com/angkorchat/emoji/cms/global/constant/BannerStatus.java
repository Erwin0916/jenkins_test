package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BannerStatus {
    REGISTERED("N"),
    LIVE("Y"),
    DELETED("D");

    private final String value;
}
