package com.mvadeyar.matrix.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EchoMatrixTest {

    private MatrixService matrixService;

    @BeforeEach
    void setup() {
        matrixService = TestHelper.createMatrixService();
    }

    // -----------------------------------------Valid Cases-----------------------------------------

    @Test
    void shouldEcho3x3Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3.csv");

        String result = matrixService.echo(file);

        String expected = "1,2,3\n4,5,6\n7,8,9";
        assertEquals(expected, result);
    }

    @Test
    void shouldEcho4x4Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_4x4.csv");

        String result = matrixService.echo(file);
        String expected = "1,2,3,4\n" +
                "5,6,7,8\n" +
                "9,10,11,12\n" +
                "13,14,15,16";
        assertEquals(expected, result);
    }

    @Test
    void shouldEcho1x1Matrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_1x1.csv");

        String result = matrixService.echo(file);

        assertEquals("1", result);
    }

    @Test
    void shouldEcho3x3NegativeMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_3x3_negativeNumbers.csv");

        String result = matrixService.echo(file);

        assertEquals("-1,-2,-3\n" +
                "-4,-5,-6\n" +
                "-7,-8,-9", result);
    }

}
