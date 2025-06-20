package com.angkorchat.emoji.cms.domain.angkor.auth.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.*;

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
public class AuthExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseExceptionDto handleUserNotFoundException(UserNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(UserLoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseExceptionDto handleUserLoginFailedException(UserLoginFailedException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(PasswordWrongCountExceededException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handlePasswordWrongCountExceededException(PasswordWrongCountExceededException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(UserGroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handleUserGroupNotFoundException(UserGroupNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(ExpiredSecondAuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseExceptionDto handleExpiredSecondAuthException(ExpiredSecondAuthException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(InvalidAdminUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleInvalidAdminUserException(InvalidAdminUserException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleUserNotRegisteredException(UserNotRegisteredException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(AuthNumberMissMatchException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseExceptionDto handleAuthNumberMissMatchException(AuthNumberMissMatchException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(PasswordWrongException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseExceptionDto handlePasswordWrongException(PasswordWrongException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }


}
