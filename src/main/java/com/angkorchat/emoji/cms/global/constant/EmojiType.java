package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmojiType {
    DEFAULT("D"),
    FREE("F"),
    PAID ("P"),
    EVENT("E");

    private final String value;
}
