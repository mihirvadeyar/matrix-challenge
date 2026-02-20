package com.mvadeyar.matrix.controller;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import com.mvadeyar.matrix.service.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Exposes REST endpoints for matrix operations.
 * Each endpoint accepts a CSV file and returns the result as plain text.
 */
@RestController
@RequestMapping("/")
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    /**
     * Returns the matrix exactly as received.
     */
    @PostMapping("/echo")
    public ResponseEntity<String> echo(@RequestParam("file") MultipartFile file)
            throws InvalidMatrixException {

        return ResponseEntity.ok(matrixService.echo(file));
    }

    /**
     * Returns the inverted (transposed) matrix.
     */
    @PostMapping("/invert")
    public ResponseEntity<String> invert(@RequestParam("file") MultipartFile file)
            throws InvalidMatrixException {

        return ResponseEntity.ok(matrixService.invert(file));
    }

    /**
     * Returns the matrix flattened into a single comma-separated line.
     */
    @PostMapping("/flatten")
    public ResponseEntity<String> flatten(@RequestParam("file") MultipartFile file)
            throws InvalidMatrixException {

        return ResponseEntity.ok(matrixService.flatten(file));
    }

    /**
     * Returns the sum of all matrix elements.
     */
    @PostMapping("/sum")
    public ResponseEntity<String> sum(@RequestParam("file") MultipartFile file)
            throws InvalidMatrixException {

        return ResponseEntity.ok(matrixService.sum(file));
    }

    /**
     * Returns the product of all matrix elements.
     */
    @PostMapping("/multiply")
    public ResponseEntity<String> multiply(@RequestParam("file") MultipartFile file)
            throws InvalidMatrixException {

        return ResponseEntity.ok(matrixService.multiply(file));
    }
}
