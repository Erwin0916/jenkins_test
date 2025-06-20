package com.angkorchat.emoji.cms.domain.studio.artist.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class ArtistNotFoundException  extends BaseException {
    private static final String CODE_KEY = "artistNotFoundException.code";
    private static final String MESSAGE_KEY = "artistNotFoundException.message";

    public ArtistNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}