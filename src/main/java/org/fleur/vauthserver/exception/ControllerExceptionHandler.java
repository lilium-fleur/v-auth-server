package org.fleur.vauthserver.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto accessDenied(AccessDeniedException ex, WebRequest request) {
        return ErrorDto.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(List.of(ex.getMessage()))
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto entityNotFound(EntityNotFoundException ex, WebRequest request) {
        return ErrorDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(List.of(ex.getMessage()))
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto badRequest(BadRequestException ex, WebRequest request) {
        return ErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(List.of(ex.getMessage()))
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto validation(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> message = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());

        return ErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto globalExceptionHandler(Exception ex, WebRequest request) {
        return ErrorDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(List.of(ex.getMessage()))
                .description(request.getDescription(false))
                .build();

    }

}
