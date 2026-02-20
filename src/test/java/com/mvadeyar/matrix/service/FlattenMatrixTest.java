package com.mvadeyar.matrix.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the "flatten" operation of MatrixService.

 * Verifies that matrices of various sizes and values are flattened into a single
 * comma-separated string.
 * Invalid input tests are handled separately in another test class.
 */
public class FlattenMatrixTest {

    private MatrixService matrixService;

    @BeforeEach
    void setup() {
        // Initialize service with parser factory and helpers
        matrixService = TestHelper.createMatrixService();
    }

    // -----------------------------------------Valid Cases-----------------------------------------

    @Test
    void shouldFlatten3x3Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3.csv");

        String result = matrixService.flatten(file);

        String expected = "1,2,3,4,5,6,7,8,9";
        assertEquals(expected, result);
    }

    @Test
    void shouldFlatten4x4Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_4x4.csv");

        String result = matrixService.flatten(file);
        String expected = "1,2,3,4," +
                "5,6,7,8," +
                "9,10,11,12," +
                "13,14,15,16";
        assertEquals(expected, result);
    }

    @Test
    void shouldFlatten5x5Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_5x5.csv");

        String result = matrixService.flatten(file);
        String expected = "1,2,3,4,5," +
                "6,7,8,9,10," +
                "11,12,13,14,15," +
                "16,17,18,19,20," +
                "21,22,23,24,25";
        assertEquals(expected, result);
    }

    @Test
    void shouldFlatten3x3NegativeMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3_negativeNumbers.csv");

        String result = matrixService.flatten(file);

        assertEquals("-1,-2,-3," +
                "-4,-5,-6," +
                "-7,-8,-9", result);
    }

}
