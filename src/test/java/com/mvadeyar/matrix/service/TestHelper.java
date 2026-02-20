package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.parser.CsvMatrixParser;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


/**
 * Utility class for unit tests.

 * Provides helper methods to:
 * 1. Create a fully-initialized MatrixService with parsers.
 * 2. Load CSV files from test resources as MockMultipartFile for testing.
 */
public class TestHelper {

    /**
     * Creates a MatrixService instance with a CSV parser pre-configured.

     * @return MatrixService ready for use in tests.
     */
    public static MatrixService createMatrixService() {
        CsvMatrixParser csvParser = new CsvMatrixParser();
        MatrixParserFactory parserFactory = new MatrixParserFactory(List.of(csvParser));
        return new MatrixService(parserFactory);
    }

    /**
     * Loads a CSV file from the classpath and wraps it as a MockMultipartFile.

     * @param resourcePath the path to the CSV file relative to resources folder
     * @return MockMultipartFile representing the file
     * @throws Exception if the file cannot be found or read
     */
    public static MockMultipartFile getFile(String resourcePath) throws Exception {
        File file = ResourceUtils.getFile("classpath:" + resourcePath);
        return new MockMultipartFile(
                "file",               // field name in form
                file.getName(),       // original filename
                "text/csv",           // content type
                new FileInputStream(file) // file content
        );
    }
}