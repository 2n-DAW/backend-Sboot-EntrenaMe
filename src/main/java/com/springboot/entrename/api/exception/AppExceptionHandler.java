package com.springboot.entrename.api.exception;

import com.springboot.entrename.domain.exception.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class AppExceptionHandler {
    // Maneja errores genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessages> handleGenericException(Exception exception) {
        return responseErrorMessages(
                Map.of("error", "internal server error"), // Error code, Error message
                HttpStatus.INTERNAL_SERVER_ERROR // Error http status
        );
    }

    // Maneja errores específicos
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorMessages> handleAppException(AppException exception) {
        return responseErrorMessages(
            Map.of(exception.getError().name(), exception.getMessage()), // Error code, Error message
            exception.getError().getStatus() // Error http status
        );
    }

    // Maneja errores de validación en los DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessages> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> fieldError = exception.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField, // Error field
                FieldError::getDefaultMessage // Error message
            ));
        return responseErrorMessages(fieldError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ErrorMessages> responseErrorMessages(Map<String, String> error, HttpStatus status) {
        ErrorMessages errorMessages = new ErrorMessages();
        error.forEach(errorMessages::addError);
        return new ResponseEntity<>(errorMessages, status);
    }
}
