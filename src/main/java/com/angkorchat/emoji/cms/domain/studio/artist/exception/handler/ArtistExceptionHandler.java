package com.angkorchat.emoji.cms.domain.studio.artist.exception.handler;

import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistAlreadyRegisteredException;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistNameDuplicateException;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistNotFoundException;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.InvalidAccessException;
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
public class ArtistExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ArtistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handleArtistNotFoundException(ArtistNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(InvalidAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleInvalidAccessException(InvalidAccessException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(ArtistNameDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleArtistNameDuplicateException(ArtistNameDuplicateException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(ArtistAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleArtistAlreadyRegisteredException(ArtistAlreadyRegisteredException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}
