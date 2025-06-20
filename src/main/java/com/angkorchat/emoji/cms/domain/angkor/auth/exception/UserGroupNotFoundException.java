package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class UserGroupNotFoundException extends BaseException {
    private static final String CODE_KEY = "groupNotFoundException.code";
    private static final String MESSAGE_KEY = "groupNotFoundException.message";

    public UserGroupNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
