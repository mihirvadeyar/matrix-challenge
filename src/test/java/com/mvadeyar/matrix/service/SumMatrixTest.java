package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SumMatrixTest {

    private MatrixService matrixService;

    @BeforeEach
    void setup() {
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

        assertEquals("-45", result);
    }

    // -----------------------------------------Invalid Cases-----------------------------------------

    @Test
    void shouldThrowFor2x2largeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNumbers.csv");

        assertThrows(InvalidMatrixException.class, () -> matrixService.sum(file));
    }

    @Test
    void shouldThrowFor2x2largeNegativeNumbersMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x2_largeNegativeNumbers.csv");

        assertThrows(InvalidMatrixException.class, () -> matrixService.sum(file));
    }

}
