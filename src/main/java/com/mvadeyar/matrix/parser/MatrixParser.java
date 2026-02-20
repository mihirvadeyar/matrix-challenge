package com.mvadeyar.matrix.parser;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatrixParser {

    public List<List<Integer>> parse(MultipartFile file) throws InvalidMatrixException;
    String getType(); // e.g., "CSV", "TSV"
}
