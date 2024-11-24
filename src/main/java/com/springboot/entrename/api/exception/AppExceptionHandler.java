package com.springboot.entrename.api.exception;

import com.springboot.entrename.domain.exception.AppException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getError().getMessage());
        return new ResponseEntity<>(errorResponse, exception.getError().getStatus());
    }

    // Clase interna para la respuesta de error
    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
