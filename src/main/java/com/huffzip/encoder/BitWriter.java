package com.huffzip.encoder;

import java.io.IOException;
import java.io.OutputStream;

public class BitWriter {

    private final OutputStream outputStream;

    private int currentByte = 0;

    private int bitCount = 0;

    public BitWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeBit(int bit) throws IOException {

        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException(
                    "Bit must be either 0 or 1"
            );
        }

        currentByte =
                (currentByte << 1) | bit;

        bitCount++;

        if (bitCount == 8) {
            flushCurrentByte();
        }
    }

    public void writeBits(String bits)
            throws IOException {

        for (int i = 0; i < bits.length(); i++) {

            char bit = bits.charAt(i);

            if (bit == '0') {
                writeBit(0);
            } else if (bit == '1') {
                writeBit(1);
            } else {
                throw new IllegalArgumentException(
                        "Invalid bit: " + bit
                );
            }
        }
    }

    public void flush() throws IOException {

        if (bitCount > 0) {

            // Move remaining bits to the left.
            currentByte =
                    currentByte << (8 - bitCount);

            outputStream.write(currentByte);

            currentByte = 0;
            bitCount = 0;
        }

        outputStream.flush();
    }

    private void flushCurrentByte()
            throws IOException {

        outputStream.write(currentByte);

        currentByte = 0;
        bitCount = 0;
    }
}