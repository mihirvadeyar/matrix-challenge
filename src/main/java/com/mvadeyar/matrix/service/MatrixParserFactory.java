package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import com.mvadeyar.matrix.parser.MatrixParser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory for retrieving the correct MatrixParser based on file type.
 * Uses Spring to automatically register all available parser implementations.
 */
@Component
public class MatrixParserFactory {

    private final Map<String, MatrixParser> parserMap;

    /**
     * Builds a lookup map of available parsers keyed by their type.
     * This makes it easy to add new formats without changing this class.
     * Currently only supports csv files as we only have the CsvMatrixParser implementation
     */
    public MatrixParserFactory(List<MatrixParser> parserList) {
        parserMap = parserList.stream()
                .collect(Collectors.toMap(
                        parser -> parser.getType().toUpperCase(),
                        Function.identity()
                ));
    }

    /**
     * Returns the parser matching the given file type.
     *
     * @param type file extension/type (e.g., "csv")
     * @return corresponding MatrixParser
     * @throws InvalidMatrixException if the type is not supported
     */
    public MatrixParser getParser(String type) throws InvalidMatrixException {
        MatrixParser parser = parserMap.get(type.toUpperCase());
        if (parser == null) {
            throw new InvalidMatrixException("Unsupported file type: " + type);
        }
        return parser;
    }
}
