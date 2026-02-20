package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.parser.CsvMatrixParser;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class TestHelper {

    public static MatrixService createMatrixService() {
        CsvMatrixParser csvParser = new CsvMatrixParser();
        MatrixParserFactory parserFactory = new MatrixParserFactory(List.of(csvParser));
        return new MatrixService(parserFactory);
    }

    public static MockMultipartFile getFile(String resourcePath) throws Exception {
        File file = ResourceUtils.getFile("classpath:" + resourcePath);
        return new MockMultipartFile(
                "file",
                file.getName(),
                "text/csv",
                new FileInputStream(file)
        );
    }
}