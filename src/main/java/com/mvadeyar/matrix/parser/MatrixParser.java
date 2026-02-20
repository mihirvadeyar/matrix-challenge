package com.mvadeyar.matrix.parser;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Strategy interface for parsing different matrix file formats.
 * Implementations handle format-specific parsing logic (e.g., CSV, TSV).
 */
public interface MatrixParser {

    /**
     * Parses the given file into a matrix of integers.
     *
     * @param file uploaded matrix file
     * @return parsed matrix as a list of rows
     * @throws InvalidMatrixException if the file is invalid or cannot be parsed
     */
    List<List<Integer>> parse(MultipartFile file) throws InvalidMatrixException;

    /**
     * Returns the supported file type (e.g., "CSV", "TSV").
     */
    String getType();
}
