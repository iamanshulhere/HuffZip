package com.huffzip.service;

import com.huffzip.decoder.HuffmanDecoder;
import com.huffzip.encoder.HuffmanEncoder;
import com.huffzip.exception.CompressionException;
import com.huffzip.exception.DecompressionException;
import com.huffzip.file.HuffmanFileReader;
import com.huffzip.file.HuffmanFileWriter;
import com.huffzip.frequency.FrequencyAnalyzer;
import com.huffzip.model.HuffmanNode;
import com.huffzip.model.HuffmanTree;
import com.huffzip.util.CompressionStatistics;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class CompressionService {

    private final FrequencyAnalyzer frequencyAnalyzer =
            new FrequencyAnalyzer();

    private final HuffmanFileWriter fileWriter =
            new HuffmanFileWriter();

    private final HuffmanFileReader fileReader =
            new HuffmanFileReader();

    private final HuffmanEncoder encoder =
            new HuffmanEncoder();

    private final HuffmanDecoder decoder =
            new HuffmanDecoder();

    public CompressionStatistics compress(
            Path inputFile,
            Path outputFile
    ) {

        validateInputFile(inputFile);

        try {

            long originalSize =
                    Files.size(inputFile);

            /*
             * STEP 1:
             * Count frequencies.
             */
            Map<Integer, Long> frequencies =
                    frequencyAnalyzer.analyze(
                            inputFile
                    );

            /*
             * STEP 2:
             * Build Huffman tree.
             */
            HuffmanTree tree =
                    new HuffmanTree(frequencies);

            HuffmanNode root =
                    tree.getRoot();

            /*
             * STEP 3:
             * Generate Huffman codes.
             */
            Map<Integer, String> codes =
                    tree.generateCodes();

            /*
             * STEP 4:
             * Write header + compressed data.
             */
            try (
                    InputStream inputStream =
                            Files.newInputStream(inputFile);

                    OutputStream outputStream =
                            Files.newOutputStream(outputFile)
            ) {

                fileWriter.writeHeader(
                        outputStream,
                        originalSize,
                        frequencies
                );

                encoder.encode(
                        inputStream,
                        outputStream,
                        codes,
                        root
                );
            }

            long compressedSize =
                    Files.size(outputFile);

            return new CompressionStatistics(
                    originalSize,
                    compressedSize
            );

        } catch (IOException e) {

            throw new CompressionException(
                    "Compression failed.",
                    e
            );
        }
    }

    public void decompress(
            Path inputFile,
            Path outputFile
    ) {

        if (!Files.exists(inputFile)) {

            throw new DecompressionException(
                    "Compressed file does not exist: "
                            + inputFile
            );
        }

        try (
                InputStream inputStream =
                        Files.newInputStream(inputFile);

                OutputStream outputStream =
                        Files.newOutputStream(outputFile)
        ) {

            /*
             * STEP 1:
             * Read header.
             */
            HuffmanFileReader.Header header =
                    fileReader.readHeader(
                            inputStream
                    );

            /*
             * STEP 2:
             * Rebuild Huffman tree.
             */
            HuffmanTree tree =
                    new HuffmanTree(
                            header.frequencies()
                    );

            HuffmanNode root =
                    tree.getRoot();

            /*
             * STEP 3:
             * Decode compressed bits.
             */
            decoder.decode(
                    inputStream,
                    outputStream,
                    root,
                    header.originalSize()
            );

        } catch (IOException e) {

            throw new DecompressionException(
                    "Decompression failed.",
                    e
            );
        }
    }

    private void validateInputFile(
            Path inputFile
    ) {

        if (!Files.exists(inputFile)) {

            throw new CompressionException(
                    "Input file does not exist: "
                            + inputFile
            );
        }

        if (!Files.isRegularFile(inputFile)) {

            throw new CompressionException(
                    "Input path is not a file: "
                            + inputFile
            );
        }
    }
}