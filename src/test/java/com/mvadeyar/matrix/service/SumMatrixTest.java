package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the "sum" operation of MatrixService.

 * Verifies that the sum of all matrix elements is calculated correctly for
 * various sizes and values, including negative numbers. Also tests overflow
 * scenarios that should throw an InvalidMatrixException.
 */
public class SumMatrixTest {

    private MatrixService matrixService;

    @BeforeEach
    void setup() {
        // Initialize service with parser factory and helpers
        matrixService = TestHelper.createMatrixService();
    }

    // -----------------------------------------Valid Cases-----------------------------------------

    @Test
    void shouldSum3x3Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3.csv");

        String result = matrixService.sum(file);

        String expected = "45";
        assertEquals(expected, result);
    }

    @Test
    void shouldSum5x5Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_5x5.csv");

        String result = matrixService.sum(file);
        String expected = "325";
        assertEquals(expected, result);
    }

    @Test
    void shouldSum3x3NegativeMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3_negativeNumbers.csv");

        String result = matrixService.sum(file);

        // Negative numbers are summed correctly
        assertEquals("-45", result);
    }

    // -----------------------------------------Invalid Cases-----------------------------------------

    @Test
    void shouldThrowFor2x2LargeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNumbers.csv");

        // Sum exceeds Integer.MAX_VALUE → overflow exception
        assertThrows(InvalidMatrixException.class, () -> matrixService.sum(file));
    }

    @Test
    void shouldThrowFor2x2LargeNegativeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNegativeNumbers.csv");

        // Sum exceeds Integer.MIN_VALUE → overflow exception
        assertThrows(InvalidMatrixException.class, () -> matrixService.sum(file));
    }

}
