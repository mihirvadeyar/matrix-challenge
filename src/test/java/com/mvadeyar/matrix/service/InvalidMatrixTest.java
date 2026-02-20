package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidMatrixTest {

    private MatrixService matrixService;
    private List<ThrowingFunction<MultipartFile, String, InvalidMatrixException>> operations;

    @BeforeEach
    void setup() {
        matrixService = TestHelper.createMatrixService();
        operations = List.of(
                matrixService::echo,
                matrixService::invert,
                matrixService::flatten,
                matrixService::sum,
                matrixService::multiply
        );
    }

    @Test
    void shouldThrowForEmptyMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_empty.csv");

        for (ThrowingFunction<MultipartFile, String, InvalidMatrixException> op : operations) {
            assertThrows(InvalidMatrixException.class, () -> op.apply(file));
        }
    }

    @Test
    void shouldThrowForInconsistentMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_inconsistent.csv");

        for (ThrowingFunction<MultipartFile, String, InvalidMatrixException> op : operations) {
            assertThrows(InvalidMatrixException.class, () -> op.apply(file));
        }
    }

    @Test
    void shouldThrowForNonNumericMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_non_numeric.csv");

        for (ThrowingFunction<MultipartFile, String, InvalidMatrixException> op : operations) {
            assertThrows(InvalidMatrixException.class, () -> op.apply(file));
        }
    }

    @Test
    void shouldThrowForNonSquareMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_2x3.csv");

        for (ThrowingFunction<MultipartFile, String, InvalidMatrixException> op : operations) {
            assertThrows(InvalidMatrixException.class, () -> op.apply(file));
        }
    }

    @Test
    void shouldThrowForInvalidTypeMatrix() throws Exception {
        MockMultipartFile file = TestHelper.getFile("matrix_invalidType.txt");

        for (ThrowingFunction<MultipartFile, String, InvalidMatrixException> op : operations) {
            assertThrows(InvalidMatrixException.class, () -> op.apply(file));
        }
    }

}


@FunctionalInterface
interface ThrowingFunction<T, R, E extends Exception> {
    R apply(T t) throws E;
}
