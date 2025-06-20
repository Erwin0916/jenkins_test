package com.angkorchat.emoji.cms.global.error;

public class InvalidInputDataFormatException extends BaseException{
    private static final String CODE_KEY = "invalidInputDataFormatException.code";
    private static final String MESSAGE_KEY = "invalidInputDataFormatException.message";

    public InvalidInputDataFormatException(String paramName) {
        super(CODE_KEY, MESSAGE_KEY, paramName);
    }
}