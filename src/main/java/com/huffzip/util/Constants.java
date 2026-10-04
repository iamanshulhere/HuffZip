package com.huffzip.util;

public final class Constants {

    private Constants() {
        // Utility class - prevent object creation
    }

    public static final int MAGIC_HEADER = 0x48554631; // "HUF1"

    public static final String HUFFMAN_EXTENSION = ".huf";

    public static final int BYTE_MIN = 0;

    public static final int BYTE_MAX = 255;
}