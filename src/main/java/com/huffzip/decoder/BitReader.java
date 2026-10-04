package com.huffzip.decoder;

import java.io.IOException;
import java.io.InputStream;

public class BitReader {

    private final InputStream inputStream;

    private int currentByte = -1;

    private int bitCount = 0;

    public BitReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int readBit() throws IOException {

        if (bitCount == 0) {

            currentByte =
                    inputStream.read();

            if (currentByte == -1) {
                return -1;
            }

            bitCount = 8;
        }

        int bit =
                (currentByte >> (bitCount - 1))
                        & 1;

        bitCount--;

        return bit;
    }
}