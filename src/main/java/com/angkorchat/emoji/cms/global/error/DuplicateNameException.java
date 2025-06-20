package com.angkorchat.emoji.cms.global.error;

public class DuplicateNameException extends BaseException {
    private static final String CODE_KEY = "duplicateNameException.code";
    private static final String MESSAGE_KEY = "duplicateNameException.message";

    public DuplicateNameException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
