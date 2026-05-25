package com.leandroftm.game_library_api.exception;

import com.leandroftm.game_library_api.exception.Enum.ErrorCode;
import com.leandroftm.game_library_api.exception.domain.DomainException;
import com.leandroftm.game_library_api.exception.domain.NotFoundException;
import com.leandroftm.game_library_api.exception.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildError(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_EXCEPTION, errors, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value " + ex.getValue() + " for parameter " + ex.getName();
        return buildError(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_EXCEPTION, message, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        return buildError(HttpStatus.FORBIDDEN, ErrorCode.INVALID_CREDENTIALS, ex.getMessage(), request);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleAuthorizationDeniedException(HttpServletRequest request, AuthorizationDeniedException ex) {
        return buildError(HttpStatus.FORBIDDEN, ErrorCode.INVALID_CREDENTIALS, ex.getMessage(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) {
        return buildError(HttpStatus.CONFLICT, ErrorCode.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(HttpServletRequest request, DomainException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(HttpServletRequest request, Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNEXPECTED_ERROR, "Unexpected error:" + ex.getMessage(), request);
    }

    //#HELPER
    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            ErrorCode errorCode,
            String message,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                errorCode,
                List.of(message),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            ErrorCode errorCode,
            List<String> messages,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                errorCode,
                messages,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(apiError);
    }
}