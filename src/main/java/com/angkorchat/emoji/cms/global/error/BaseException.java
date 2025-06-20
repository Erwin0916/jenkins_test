package com.angkorchat.emoji.cms.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class BaseException extends RuntimeException {
    private String codeKey;
    private String messageKey;
    private Object data;
    private String errorField;
    private static final String CODE_KEY = "unknownException.code";
    private static final String MESSAGE_KEY = "unknownException.message";

    public BaseException() {
        this.codeKey = CODE_KEY;
        this.messageKey = MESSAGE_KEY;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BaseException(String codeKey, String messageKey) {
        this.codeKey = codeKey;
        this.messageKey = messageKey;
    }

    public BaseException(String codeKey, String messageKey, Object data) {
        this.codeKey = codeKey;
        this.messageKey = messageKey;
        this.data = data;
    }

    public BaseException(String codeKey, String messageKey, String errorField) {
        this.codeKey = codeKey;
        this.messageKey = messageKey;
        this.errorField = errorField;
    }
}
