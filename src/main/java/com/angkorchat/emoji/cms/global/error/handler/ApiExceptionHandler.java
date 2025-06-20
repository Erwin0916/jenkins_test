package com.angkorchat.emoji.cms.global.error.handler;

import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryAlreadyLiveException;
import com.angkorchat.emoji.cms.global.config.security.exception.SecurityException;
import com.angkorchat.emoji.cms.global.error.*;
import com.angkorchat.emoji.cms.global.error.dto.BaseExceptionDto;
import com.angkorchat.emoji.cms.global.util.ExceptionResponseUtils;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import jakarta.validation.ConstraintViolationException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.ServletRequest;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    protected BaseExceptionDto handleBaseException(BaseException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLSyntaxErrorException.class)
    protected BaseExceptionDto handleSQLSyntaxErrorException(SQLSyntaxErrorException ex, ServletRequest request) {
        String errorCode = "unknownException.code";
        String errorMessage = "unknownException.message";

        String errorField = ex.getMessage();
        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, messageSource);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    protected BaseExceptionDto handleSQLException(SQLException ex, ServletRequest request) {
        String errorCode = "unknownException.code";
        String errorMessage = "unknownException.message";

        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, messageSource);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MyBatisSystemException.class)
    protected BaseExceptionDto handleMyBatisSystemException(MyBatisSystemException ex, ServletRequest request) {
        String errorCode = "unknownException.code";
        String errorMessage = "unknownException.message";

        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, messageSource);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RedisDataInputException.class)
    protected BaseExceptionDto handleRedisDataInputException(RedisDataInputException ex, ServletRequest request) {

        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RedisKeyNotFoundException.class)
    protected BaseExceptionDto handleRedisKeyNotFoundException(RedisKeyNotFoundException ex, ServletRequest request) {

        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException.class)
    protected BaseExceptionDto handleMissingRequestCookieException(MissingRequestCookieException ex, ServletRequest request) {
        String errorCode = "refreshTokenInValidException.code";
        String errorMessage = "refreshTokenInValidException.message";

        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, messageSource);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SecurityException.class)
    protected BaseExceptionDto handleSecurityException(SecurityException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected BaseExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, ServletRequest request) {
        String errorCode = "invalidInputDataFormatException.code";
        String errorMessage = "invalidInputDataFormatException.message";
        String errorFiled = Objects.requireNonNull(ex.getFieldError()).getField();
        List<String> detailMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        String errorDetail = errorFiled + " : " + detailMessages;

        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, errorDetail, messageSource);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    protected BaseExceptionDto dataNotFoundException(DataNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    //path variable, param validation 예외
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseExceptionDto handleConstraintViolationException(ConstraintViolationException ex, ServletRequest request) {
        String errorCode = "invalidInputDataFormatException.code";
        String errorMessage = "invalidInputDataFormatException.message";

        String errorField = ex.getMessage();

        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, errorField, messageSource);
    }

    // 스프링 타입 매핑 오류
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseExceptionDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, ServletRequest request) {
        String errorCode = "invalidInputDataFormatException.code";
        String errorMessage = "invalidInputDataFormatException.message";
        String errorFiled = ex.getName();

        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, errorFiled, messageSource);
    }

    //공통 입력 예외 BaseException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputDataFormatException.class)
    public BaseExceptionDto handleInvalidInputDataFormatException(InvalidInputDataFormatException ex, ServletRequest request) {
        String errorCode = "invalidInputDataFormatException.code";
        String errorMessage = "invalidInputDataFormatException.message";
        String errorFiled = ex.getErrorField();
        return ExceptionResponseUtils.createErrorResponse(request, errorCode, errorMessage, errorFiled, messageSource);
    }

    //외부 Api 호출 예외
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiRequestErrorException.class)
    public BaseExceptionDto handleApiRequestErrorException(ApiRequestErrorException ex, ServletRequest request) {

        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), ex.getErrorField(),  messageSource);
    }

    //외부 Api 호출 예외
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateNameException.class)
    public BaseExceptionDto handleDuplicateNameException(DuplicateNameException ex, ServletRequest request) {

        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }



}
