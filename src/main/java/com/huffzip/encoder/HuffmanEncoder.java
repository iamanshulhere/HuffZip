package com.huffzip.encoder;

import com.huffzip.model.HuffmanNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class HuffmanEncoder {

    public void encode(
            InputStream inputStream,
            OutputStream outputStream,
            Map<Integer, String> codes,
            HuffmanNode root
    ) throws IOException {

        // Empty file
        if (root == null) {
            return;
        }

        /*
         * If the file contains only one unique byte,
         * no compressed payload bits are required.
         *
         * The decoder knows:
         * 1. The symbol
         * 2. The original file size
         *
         * Therefore it can reconstruct the entire file.
         */
        if (root.isLeaf()) {
            return;
        }

        BitWriter bitWriter =
                new BitWriter(outputStream);

        int value;

        while ((value = inputStream.read()) != -1) {

            String code = codes.get(value);

            if (code == null) {
                throw new IOException(
                        "No Huffman code found for byte: " + value
                );
            }

            bitWriter.writeBits(code);
        }

        bitWriter.flush();
    }
}