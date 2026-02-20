package com.mvadeyar.matrix.service;

import com.mvadeyar.matrix.exception.InvalidMatrixException;
import com.mvadeyar.matrix.parser.MatrixParser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MatrixParserFactory {

    private final Map<String, MatrixParser> parserMap;

    public MatrixParserFactory(List<MatrixParser> parserList) {
        parserMap = parserList.stream()
                .collect(Collectors.toMap(
                        parser -> parser.getType().toUpperCase(),
                        Function.identity()
                ));
    }

    public MatrixParser getParser(String type) throws InvalidMatrixException {
        MatrixParser parser = parserMap.get(type.toUpperCase());
        if (parser == null) {
            throw new InvalidMatrixException("Unsupported file type: " + type);
        }
        return parser;
    }
}
