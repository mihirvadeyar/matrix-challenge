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

    public MatrixService(MatrixParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    public static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ""; // no extension
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toUpperCase();
    }

    private MatrixParser getParserForFile(MultipartFile file) throws InvalidMatrixException {
        String filename = file.getOriginalFilename();
        String fileType = getFileExtension(filename); // CSV, TSV, etc.
        return parserFactory.getParser(fileType);
    }

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

    private List<List<Integer>> parseAndValidateMatrix(MultipartFile file)
            throws InvalidMatrixException {

        MatrixParser parser = getParserForFile(file);
        List<List<Integer>> matrix = parser.parse(file);
        validateSquareMatrix(matrix);
        return matrix;
    }


    public String echo(MultipartFile file) throws InvalidMatrixException{

        List<List<Integer>> matrix = parseAndValidateMatrix(file);
        return format(matrix);
    }

    private String format(List<List<Integer>> matrix) {
        String formattedMatrix = matrix.stream()
                .map(row -> row.stream()
                        .map(String::valueOf)      // convert each integer to string
                        .collect(Collectors.joining(",")) // join columns with comma
                )
                .collect(Collectors.joining("\n")); // join rows with newline
        System.out.println(formattedMatrix);
        return formattedMatrix;
    }

    public String invert(MultipartFile file) throws InvalidMatrixException{

        List<List<Integer>> matrix = parseAndValidateMatrix(file);

        return format(invertMatrix(matrix));
    }

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

    public String flatten(MultipartFile file) throws InvalidMatrixException{

        List<List<Integer>> matrix = parseAndValidateMatrix(file);

        return matrix.stream()
                .flatMap(List::stream)        // flatten rows into one stream
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public String sum(MultipartFile file) throws InvalidMatrixException{

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

    public String multiply(MultipartFile file) throws InvalidMatrixException{

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
