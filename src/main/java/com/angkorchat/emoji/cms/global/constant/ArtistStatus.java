package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArtistStatus {
    APPROVED(1),
    BLOCKED(2),
    DELETED(3);

    private final Integer value;
}
