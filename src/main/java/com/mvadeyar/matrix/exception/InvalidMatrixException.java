package com.mvadeyar.matrix.exception;

// Custom exception for invalid matrix operations
public class InvalidMatrixException extends Exception {

    // Default constructor
    public InvalidMatrixException() {
        super("Invalid matrix operation.");
    }

    // Constructor with custom message
    public InvalidMatrixException(String message) {
        super(message);
    }
}