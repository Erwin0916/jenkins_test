package com.angkorchat.emoji.cms.domain.angkor.admin.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.admin.exception.AdminEmailDuplicateException;

import com.angkorchat.emoji.cms.domain.angkor.admin.exception.GroupMemberExistException;
import com.angkorchat.emoji.cms.domain.angkor.admin.exception.GroupNameDuplicateException;
import com.angkorchat.emoji.cms.domain.angkor.admin.exception.GroupNotFoundException;
import com.angkorchat.emoji.cms.global.error.dto.BaseExceptionDto;
import com.angkorchat.emoji.cms.global.util.ExceptionResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.ServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class AdminExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(AdminEmailDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleAdminEmailDuplicateException(AdminEmailDuplicateException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handleGroupNotFoundException(GroupNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(GroupMemberExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleGroupMemberExistException(GroupMemberExistException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(GroupNameDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleGroupNameDuplicateException(GroupNameDuplicateException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}