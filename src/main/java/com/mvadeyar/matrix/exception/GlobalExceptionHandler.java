package com.mvadeyar.matrix.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Handles matrix-related exceptions globally.
 * Returns meaningful error messages to the client.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Catch InvalidMatrixException and respond with 400 Bad Request.
     */
    @ExceptionHandler(InvalidMatrixException.class)
    public ResponseEntity<String> handleInvalidMatrix(InvalidMatrixException e) {
        // Send the exception message back so the client knows what went wrong
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
