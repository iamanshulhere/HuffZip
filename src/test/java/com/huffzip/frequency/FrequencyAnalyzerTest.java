package com.huffzip.frequency;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrequencyAnalyzerTest {

    @Test
    void shouldCountByteFrequencies()
            throws Exception {

        Path file =
                Files.createTempFile(
                        "huffzip-frequency",
                        ".txt"
                );

        Files.writeString(
                file,
                "aaabbc"
        );

        FrequencyAnalyzer analyzer =
                new FrequencyAnalyzer();

        Map<Integer, Long> result =
                analyzer.analyze(file);

        assertEquals(
                3L,
                result.get((int) 'a')
        );

        assertEquals(
                2L,
                result.get((int) 'b')
        );

        assertEquals(
                1L,
                result.get((int) 'c')
        );

        Files.deleteIfExists(file);
    }
}