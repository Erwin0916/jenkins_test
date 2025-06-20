package com.angkorchat.emoji.cms.domain.studio.artist.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class ArtistAlreadyRegisteredException extends BaseException {
    private static final String CODE_KEY = "artistAlreadyRegisteredException.code";
    private static final String MESSAGE_KEY = "artistAlreadyRegisteredException.message";

    public ArtistAlreadyRegisteredException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}