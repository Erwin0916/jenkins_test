package com.angkorchat.emoji.cms.domain.angkor.artist.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class ArtistAccountAlreadyRegisteredException extends BaseException {
    private static final String CODE_KEY = "artistAccountAlreadyRegisteredException.code";
    private static final String MESSAGE_KEY = "artistAccountAlreadyRegisteredException.message";

    public ArtistAccountAlreadyRegisteredException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}