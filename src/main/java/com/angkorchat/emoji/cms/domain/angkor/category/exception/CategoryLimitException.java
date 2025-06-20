package com.angkorchat.emoji.cms.domain.angkor.category.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class CategoryLimitException extends BaseException {
    private static final String CODE_KEY = "categoryLimitException.code";
    private static final String MESSAGE_KEY = "categoryLimitException.message";

    public CategoryLimitException(String param) {
        super(CODE_KEY, MESSAGE_KEY, param);
    }
}