package com.mvadeyar.matrix.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvertMatrixTest {

    private MatrixService matrixService;

    @BeforeEach
    void setup() {
        matrixService = TestHelper.createMatrixService();
    }

    // -----------------------------------------Valid Cases-----------------------------------------

    @Test
    void shouldInvert3x3Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3.csv");

        String result = matrixService.invert(file);

        String expected = "1,4,7\n2,5,8\n3,6,9";
        assertEquals(expected, result);
    }

    @Test
    void shouldInvert2x2LargeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNumbers.csv");

        String result = matrixService.invert(file);

        String expected = "2147483647,21238\n" +
                "2147483645,10000";
        assertEquals(expected, result);
    }


    @Test
    void shouldInvert3x3NegativeMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3_negativeNumbers.csv");

        String result = matrixService.invert(file);

        assertEquals("-1,-4,-7\n" +
                "-2,-5,-8\n" +
                "-3,-6,-9", result);
    }
}