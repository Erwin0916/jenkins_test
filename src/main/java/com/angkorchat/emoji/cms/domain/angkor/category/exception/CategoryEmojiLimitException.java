package com.angkorchat.emoji.cms.domain.angkor.category.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class CategoryEmojiLimitException extends BaseException {
    private static final String CODE_KEY = "categoryEmojiLimitException.code";
    private static final String MESSAGE_KEY = "categoryEmojiLimitException.message";

    public CategoryEmojiLimitException(String param) {
        super(CODE_KEY, MESSAGE_KEY, param);
    }
}