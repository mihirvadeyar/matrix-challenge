package com.mvadeyar.matrix.parser;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvMatrixParser implements MatrixParser{


    @Override
    public String getType() {
        return "CSV";
    }

    @Override
    public List<List<Integer>> parse(MultipartFile file) throws InvalidMatrixException {

        if (file == null || file.isEmpty()) {
            throw new InvalidMatrixException("File is empty");
        }
        List<List<Integer>> matrix = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;
            int expectedColumns = -1;
            int rowNumber = 0;

            while ((line = reader.readLine()) != null) {
                rowNumber++;

                if (line.isBlank()) {
                    throw new InvalidMatrixException("Empty row detected at row " + rowNumber);
                }

                String[] values = line.split(",");

                if (expectedColumns == -1) {
                    expectedColumns = values.length;
                } else if (values.length != expectedColumns) {
                    throw new InvalidMatrixException(
                            "Row " + rowNumber + " has inconsistent column count."
                    );
                }

                List<Integer> row = new ArrayList<>();

                for (String value : values) {
                    try {
                        row.add(Integer.parseInt(value.trim()));
                    } catch (NumberFormatException e) {
                        throw new InvalidMatrixException(
                                "Non-numeric value at row " + rowNumber + ": " + value
                        );
                    }
                }

                matrix.add(row);
            }

        } catch (IOException e) {
            throw new InvalidMatrixException("Failed to read file.");
        }

        return matrix;
    }
}
