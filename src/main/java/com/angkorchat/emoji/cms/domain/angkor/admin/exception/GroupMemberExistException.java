
package com.angkorchat.emoji.cms.domain.angkor.admin.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class GroupMemberExistException extends BaseException {
    private static final String CODE_KEY = "groupMemberExistException.code";
    private static final String MESSAGE_KEY = "groupMemberExistException.message";

    public GroupMemberExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}