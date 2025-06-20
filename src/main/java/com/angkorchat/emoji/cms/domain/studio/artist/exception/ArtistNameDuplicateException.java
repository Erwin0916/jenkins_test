package com.angkorchat.emoji.cms.domain.studio.artist.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class ArtistNameDuplicateException extends BaseException {
    private static final String CODE_KEY = "artistNameDuplicateException.code";
    private static final String MESSAGE_KEY = "artistNameDuplicateException.message";

    public ArtistNameDuplicateException(String param) {
        super(CODE_KEY, MESSAGE_KEY, param);
    }
}