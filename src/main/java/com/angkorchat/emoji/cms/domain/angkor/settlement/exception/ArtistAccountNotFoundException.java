package com.angkorchat.emoji.cms.domain.angkor.settlement.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class ArtistAccountNotFoundException extends BaseException {
    private static final String CODE_KEY = "artistAccountNotFoundException.code";
    private static final String MESSAGE_KEY = "artistAccountNotFoundException.message";

    public ArtistAccountNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}