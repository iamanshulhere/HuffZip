package com.huffzip.file;

import com.huffzip.exception.DecompressionException;
import com.huffzip.util.Constants;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanFileReader {

    public Header readHeader(
            InputStream inputStream
    ) throws IOException {

        DataInputStream dataInput =
                new DataInputStream(inputStream);

        int magic =
                dataInput.readInt();

        if (magic != Constants.MAGIC_HEADER) {

            throw new DecompressionException(
                    "Invalid HuffZip file."
            );
        }

        long originalSize =
                dataInput.readLong();

        if (originalSize < 0) {
            throw new DecompressionException(
                    "Invalid original file size."
            );
        }

        int uniqueSymbols =
                dataInput.readInt();

        if (uniqueSymbols < 0 ||
                uniqueSymbols > 256) {

            throw new DecompressionException(
                    "Invalid symbol count."
            );
        }

        Map<Integer, Long> frequencies =
                new TreeMap<>();

        for (int i = 0;
             i < uniqueSymbols;
             i++) {

            int symbol =
                    dataInput.readUnsignedByte();

            long frequency =
                    dataInput.readLong();

            if (frequency <= 0) {

                throw new DecompressionException(
                        "Invalid frequency."
                );
            }

            if (frequencies.containsKey(symbol)) {

                throw new DecompressionException(
                        "Duplicate symbol in header."
                );
            }

            frequencies.put(
                    symbol,
                    frequency
            );
        }

        return new Header(
                originalSize,
                frequencies
        );
    }

    public record Header(
            long originalSize,
            Map<Integer, Long> frequencies
    ) {
    }
}