package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmojiStatus {
    FOR_SALE("002001"),
    PAUSED("002002"),
    DELETED("002003"),
    IN_REVIEW("002004"),
    REJECTED("002005");

    private final String value;
}
