package com.angkorchat.emoji.cms.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestType {
    CMS("cms"),
    STUDIO("studio"),
    EXCEPTION("exception");

    private final String type;
}
