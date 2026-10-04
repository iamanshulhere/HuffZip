package com.huffzip.decoder;

import com.huffzip.model.HuffmanNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HuffmanDecoder {

    public void decode(
            InputStream inputStream,
            OutputStream outputStream,
            HuffmanNode root,
            long originalSize
    ) throws IOException {

        if (originalSize == 0) {
            return;
        }

        if (root == null) {
            throw new IOException(
                    "Huffman tree is empty."
            );
        }

        /*
         * Single-symbol case.
         */
        if (root.isLeaf()) {

            for (long i = 0; i < originalSize; i++) {

                outputStream.write(
                        root.getSymbol()
                );
            }

            return;
        }

        BitReader bitReader =
                new BitReader(inputStream);

        HuffmanNode current = root;

        long decodedBytes = 0;

        while (decodedBytes < originalSize) {

            int bit = bitReader.readBit();

            if (bit == -1) {
                throw new IOException(
                        "Unexpected end of compressed data."
                );
            }

            if (bit == 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }

            if (current == null) {
                throw new IOException(
                        "Invalid Huffman data."
                );
            }

            if (current.isLeaf()) {

                outputStream.write(
                        current.getSymbol()
                );

                decodedBytes++;

                current = root;
            }
        }
    }
}