package com.angkorchat.emoji.cms.domain.angkor.emoji.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class EmojiNameDuplicateException extends BaseException {
    private static final String CODE_KEY = "emojiNameDuplicateException.code";
    private static final String MESSAGE_KEY = "emojiNameDuplicateException.message";

    public EmojiNameDuplicateException(String paramName) {
        super(CODE_KEY, MESSAGE_KEY, paramName);
    }
}