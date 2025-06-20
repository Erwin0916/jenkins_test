package com.angkorchat.emoji.cms.domain.angkor.code.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.code.exception.CodeNameDuplicateException;
import com.angkorchat.emoji.cms.domain.angkor.code.exception.MajorCodeNotFoundException;

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
public class CodeExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(MajorCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handleMajorCodeNotFoundException(MajorCodeNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(CodeNameDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleCodeNameDuplicateException(CodeNameDuplicateException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}