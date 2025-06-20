package com.angkorchat.emoji.cms.global.error;

public class MethodArgumentNotValidException extends BaseException {
    private static final String CODE_KEY = "methodArgumentNotValidException.code";
    private static final String MESSAGE_KEY = "methodArgumentNotValidException.message";

    public MethodArgumentNotValidException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
