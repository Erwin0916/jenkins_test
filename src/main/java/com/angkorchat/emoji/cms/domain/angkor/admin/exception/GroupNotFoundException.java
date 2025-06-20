
package com.angkorchat.emoji.cms.domain.angkor.admin.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class GroupNotFoundException extends BaseException {
    private static final String CODE_KEY = "groupNotFoundException.code";
    private static final String MESSAGE_KEY = "groupNotFoundException.message";

    public GroupNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}