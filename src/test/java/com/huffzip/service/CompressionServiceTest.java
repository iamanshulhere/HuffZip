package com.huffzip.service;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompressionServiceTest {

    @Test
    void shouldCompressAndDecompressFile()
            throws Exception {

        Path directory =
                Files.createTempDirectory(
                        "huffzip-test"
                );

        Path input =
                directory.resolve("input.txt");

        Path compressed =
                directory.resolve("compressed.huf");

        Path restored =
                directory.resolve("restored.txt");

        String original =
                """
                HuffZip is a lossless compression project.
                Huffman coding uses a binary tree and a greedy algorithm.
                This file is used for integration testing.
                """;

        Files.writeString(
                input,
                original,
                StandardCharsets.UTF_8
        );

        CompressionService service =
                new CompressionService();

        service.compress(
                input,
                compressed
        );

        assertTrue(
                Files.exists(compressed)
        );

        service.decompress(
                compressed,
                restored
        );

        byte[] originalBytes =
                Files.readAllBytes(input);

        byte[] restoredBytes =
                Files.readAllBytes(restored);

        assertArrayEquals(
                originalBytes,
                restoredBytes
        );

        // Cleanup
        Files.deleteIfExists(input);
        Files.deleteIfExists(compressed);
        Files.deleteIfExists(restored);
        Files.deleteIfExists(directory);
    }
}