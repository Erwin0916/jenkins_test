package com.angkorchat.emoji.cms.domain.angkor.emoji.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class EmojiNotFoundException extends BaseException {
    private static final String CODE_KEY = "emojiNotFoundException.code";
    private static final String MESSAGE_KEY = "emojiNotFoundException.message";

    public EmojiNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}