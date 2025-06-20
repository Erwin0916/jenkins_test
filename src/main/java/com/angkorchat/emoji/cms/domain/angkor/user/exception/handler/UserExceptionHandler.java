package com.angkorchat.emoji.cms.domain.angkor.user.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.user.exception.WithdrawalUserException;
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
public class UserExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(WithdrawalUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleGameDeleteFailException(WithdrawalUserException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}
