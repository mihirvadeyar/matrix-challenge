package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import com.mvadeyar.matrix.parser.MatrixParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MatrixService {

    private final MatrixParserFactory parserFactory;

    /**
     * Service responsible for all matrix operations.
     * Delegates parsing to a format-specific parser via the factory.
     */
    public MatrixService(MatrixParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    /**
     * Extracts the file extension from the filename.
     * Returns empty string if no extension is present.
     */
    private static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toUpperCase();
    }

    /**
     * Determines the correct parser based on file extension.
     */
    private MatrixParser getParserForFile(MultipartFile file) throws InvalidMatrixException {
        String filename = file.getOriginalFilename();
        String fileType = getFileExtension(filename);
        return parserFactory.getParser(fileType);
    }

    /**
     * Ensures the matrix is square (rows = columns).
     */
    private void validateSquareMatrix(List<List<Integer>> matrix)
            throws InvalidMatrixException {

        int rows = matrix.size();

        for (List<Integer> row : matrix) {
            if (row.size() != rows) {
                throw new InvalidMatrixException(
                        "Invalid matrix: matrix must be square (rows must equal columns)."
                );
            }
        }
    }

    /**
     * Parses the file and validates matrix structure in one place.
     * Keeps other methods clean and consistent.
     */
    private List<List<Integer>> parseAndValidateMatrix(MultipartFile file)
            throws InvalidMatrixException {

        MatrixParser parser = getParserForFile(file);
        List<List<Integer>> matrix = parser.parse(file);
        validateSquareMatrix(matrix);
        return matrix;
    }

    /**
     * Returns matrix exactly as provided, formatted.
     */
    public String echo(MultipartFile file) throws InvalidMatrixException {
        List<List<Integer>> matrix = parseAndValidateMatrix(file);
        return format(matrix);
    }

    /**
     * Formats a matrix into comma-separated rows.
     */
    private String format(List<List<Integer>> matrix) {
        return matrix.stream()
                .map(row -> row.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns the inverted (transposed) matrix.
     */
    public String invert(MultipartFile file) throws InvalidMatrixException {
        List<List<Integer>> matrix = parseAndValidateMatrix(file);
        return format(invertMatrix(matrix));
    }

    /**
     * Inverts or Transposes the matrix (rows become columns).
     */
    private List<List<Integer>> invertMatrix(List<List<Integer>> matrix) {
        int rowCount = matrix.size();
        int colCount = matrix.get(0).size();

        List<List<Integer>> result = new ArrayList<>();

        for (int col = 0; col < colCount; col++) {
            List<Integer> newRow = new ArrayList<>();
            for (int row = 0; row < rowCount; row++) {
                newRow.add(matrix.get(row).get(col));
            }
            result.add(newRow);
        }

        return result;
    }

    /**
     * Flattens the matrix into a single comma-separated line.
     */
    public String flatten(MultipartFile file) throws InvalidMatrixException {
        List<List<Integer>> matrix = parseAndValidateMatrix(file);

        return matrix.stream()
                .flatMap(List::stream)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    /**
     * Returns the sum of all matrix elements.
     * Detects integer overflow explicitly.
     */
    public String sum(MultipartFile file) throws InvalidMatrixException {

        List<List<Integer>> matrix = parseAndValidateMatrix(file);

        long sum = 0L;

        for (List<Integer> row : matrix) {
            for (int val : row) {
                long temp = sum + val;
                if (temp > Integer.MAX_VALUE || temp < Integer.MIN_VALUE) {
                    throw new InvalidMatrixException(
                            "Integer overflow occurred while summing matrix elements."
                    );
                }
                sum = temp;
            }
        }

        return String.valueOf((int) sum);
    }

    /**
     * Returns the product of all matrix elements.
     * Detects integer overflow explicitly.
     */
    public String multiply(MultipartFile file) throws InvalidMatrixException {

        List<List<Integer>> matrix = parseAndValidateMatrix(file);

        long product = 1L;

        for (List<Integer> row : matrix) {
            for (int val : row) {
                long temp = product * val;
                if (temp > Integer.MAX_VALUE || temp < Integer.MIN_VALUE) {
                    throw new InvalidMatrixException(
                            "Integer overflow occurred while multiplying matrix elements."
                    );
                }
                product = temp;
            }
        }

        return String.valueOf((int) product);
    }
}
