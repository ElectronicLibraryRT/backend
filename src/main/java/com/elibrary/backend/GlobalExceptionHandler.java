package com.elibrary.backend;

import com.elibrary.backend.shared.dto.ErrorDto;
import com.elibrary.backend.shared.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleAuthorNotFound(ResourceNotFoundException ex) {
        Map<String, Object> errorDetails = Map.of(
            "resource", ex.getResourceName(),
            "field", ex.getFieldName(),
            "value", ex.getFieldValue()
        );

        ErrorDto error = new ErrorDto(
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            errorDetails
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String field = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            errors.put(field, violation.getMessage());
        }

        ErrorDto error = new ErrorDto(
            "VALIDATION_ERROR",
            "Validation error of path parameters or body",
            errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = String.format("Invalid value '%s' for param '%s'",
            ex.getValue(),
            ex.getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorDto(
                "TYPE_MISMATCH",
                error,
                Map.of(
                    "parameter", ex.getName(),
                    "value", ex.getValue() != null ? ex.getValue().toString() : "null",
                    "requiredType", ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown"
                )
            ));
    }
}