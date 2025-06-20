
package com.angkorchat.emoji.cms.domain.angkor.admin.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class GroupNameDuplicateException extends BaseException {
    private static final String CODE_KEY = "groupNameDuplicateException.code";
    private static final String MESSAGE_KEY = "groupNameDuplicateException.message";

    public GroupNameDuplicateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}