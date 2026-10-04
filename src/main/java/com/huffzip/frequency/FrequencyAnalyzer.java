package com.huffzip.frequency;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class FrequencyAnalyzer {

    public Map<Integer, Long> analyze(Path inputFile)
            throws IOException {

        Map<Integer, Long> frequencies = new TreeMap<>();

        try (InputStream inputStream =
                     Files.newInputStream(inputFile)) {

            int value;

            while ((value = inputStream.read()) != -1) {

                frequencies.merge(
                        value,
                        1L,
                        Long::sum
                );
            }
        }

        return frequencies;
    }
}