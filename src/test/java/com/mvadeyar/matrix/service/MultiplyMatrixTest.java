package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the "multiply" operation of MatrixService.

 * Verifies that the product of all matrix elements is calculated correctly
 * for various sizes, including zero and negative numbers. Also tests
 * overflow scenarios and invalid inputs.
 */
public class MultiplyMatrixTest {

    private MatrixService matrixService;

    @BeforeEach
    void setup() {
        // Initialize service with parser factory and helpers
        matrixService = TestHelper.createMatrixService();
    }

    // -----------------------------------------Valid Cases-----------------------------------------

    @Test
    void shouldMultiply3x3Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3.csv");

        String result = matrixService.multiply(file);

        String expected = "362880";
        assertEquals(expected, result);
    }

    @Test
    void shouldMultiply2x2MatrixWithZero() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2.csv");

        String result = matrixService.multiply(file);

        // Any matrix with zero yields product 0
        String expected = "0";
        assertEquals(expected, result);
    }

    @Test
    void shouldMultiply3x3NegativeMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3_negativeNumbers.csv");

        String result = matrixService.multiply(file);

        // Negative numbers handled correctly
        assertEquals("-362880", result);
    }

    // -----------------------------------------Invalid Cases-----------------------------------------

    @Test
    void shouldThrowFor5x5Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_5x5.csv");

        // Overflow triggers exception
        assertThrows(InvalidMatrixException.class, () -> matrixService.multiply(file));
    }

    @Test
    void shouldThrowFor2x2largeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNumbers.csv");

        // Overflow triggers exception
        assertThrows(InvalidMatrixException.class, () -> matrixService.multiply(file));
    }

    @Test
    void shouldThrowFor2x2LargeNegativeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNegativeNumbers.csv");

        // Overflow with negatives triggers exception
        assertThrows(InvalidMatrixException.class, () -> matrixService.multiply(file));
    }
}
