package com.mvadeyar.matrix.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidMatrixException.class)
    public ResponseEntity<String> handleInvalidMatrix(InvalidMatrixException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
