package com.angkorchat.emoji.cms.domain.angkor.category.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryAlreadyLiveException;
import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryEmojiLimitException;
import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryLimitException;
import com.angkorchat.emoji.cms.domain.angkor.category.exception.CategoryNotFoundException;
import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;
import com.angkorchat.emoji.cms.global.error.dto.BaseExceptionDto;
import com.angkorchat.emoji.cms.global.util.ExceptionResponseUtils;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class CategoryExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(CategoryAlreadyLiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseExceptionDto handleCategoryAlreadyLiveException(CategoryAlreadyLiveException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handleCategoryNotFoundException(CategoryNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(CategoryEmojiLimitException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleCategoryEmojiLimitException(CategoryEmojiLimitException ex, ServletRequest request) {
        String errorFiled = ex.getErrorField();
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), errorFiled, messageSource);
    }

    @ExceptionHandler(CategoryLimitException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleCategoryLimitException(CategoryLimitException ex, ServletRequest request) {
        String errorFiled = ex.getErrorField();
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), errorFiled, messageSource);
    }
}