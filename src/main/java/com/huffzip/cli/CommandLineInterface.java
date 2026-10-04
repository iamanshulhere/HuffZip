package com.huffzip.cli;

import com.huffzip.exception.CompressionException;
import com.huffzip.exception.DecompressionException;
import com.huffzip.service.CompressionService;
import com.huffzip.util.CompressionStatistics;

import java.nio.file.Path;

public class CommandLineInterface {

    private final CompressionService compressionService =
            new CompressionService();

    public void run(String[] args) {

        if (args.length != 3) {

            printUsage();
            return;
        }

        String command =
                args[0].toLowerCase();

        Path input =
                Path.of(args[1]);

        Path output =
                Path.of(args[2]);

        try {

            switch (command) {

                case "compress" -> {

                    System.out.println(
                            "Starting compression..."
                    );

                    CompressionStatistics statistics =
                            compressionService.compress(
                                    input,
                                    output
                            );

                    System.out.println(
                            "Compression successful!"
                    );

                    System.out.println(
                            "Output: " + output
                    );

                    statistics.print();
                }

                case "decompress" -> {

                    System.out.println(
                            "Starting decompression..."
                    );

                    compressionService.decompress(
                            input,
                            output
                    );

                    System.out.println(
                            "Decompression successful!"
                    );

                    System.out.println(
                            "Output: " + output
                    );
                }

                default -> {

                    System.out.println(
                            "Unknown command: "
                                    + command
                    );

                    printUsage();
                }
            }

        } catch (
                CompressionException |
                DecompressionException e
        ) {

            System.err.println(
                    "ERROR: " + e.getMessage()
            );
        }
    }

    private void printUsage() {

        System.out.println();
        System.out.println("HuffZip - Huffman File Compressor");
        System.out.println();
        System.out.println("Usage:");
        System.out.println();
        System.out.println(
                "  compress <input> <output.huf>"
        );
        System.out.println(
                "  decompress <input.huf> <output>"
        );
        System.out.println();
        System.out.println("Examples:");
        System.out.println();
        System.out.println(
                "  java -jar HuffZip.jar " +
                        "compress input/sample.txt output/sample.huf"
        );
        System.out.println();
        System.out.println(
                "  java -jar HuffZip.jar " +
                        "decompress output/sample.huf " +
                        "output/restored.txt"
        );
        System.out.println();
    }
}