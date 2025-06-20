
package com.angkorchat.emoji.cms.domain.angkor.code.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class CodeNameDuplicateException extends BaseException {
    private static final String CODE_KEY = "codeNameDuplicateException.code";
    private static final String MESSAGE_KEY = "codeNameDuplicateException.message";

    public CodeNameDuplicateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}