package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {
    STOP("S"),
    MOVING("M");

    private final String value;
}
