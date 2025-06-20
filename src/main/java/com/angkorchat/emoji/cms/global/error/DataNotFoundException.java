package com.angkorchat.emoji.cms.global.error;

public class DataNotFoundException extends BaseException {
    private static final String CODE_KEY = "dataNotFoundException.code";
    private static final String MESSAGE_KEY = "dataNotFoundException.message";

    public DataNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
