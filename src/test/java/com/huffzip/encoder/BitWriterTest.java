package com.huffzip.encoder;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitWriterTest {

    @Test
    void shouldWriteBitsAsByte()
            throws Exception {

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        BitWriter writer =
                new BitWriter(output);

        writer.writeBits("10110010");

        writer.flush();

        byte[] result =
                output.toByteArray();

        assertEquals(1, result.length);

        assertEquals(
                0b10110010,
                result[0] & 0xFF
        );
    }

    @Test
    void shouldPadIncompleteByte()
            throws Exception {

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        BitWriter writer =
                new BitWriter(output);

        writer.writeBits("101");

        writer.flush();

        byte[] result =
                output.toByteArray();

        assertEquals(1, result.length);

        // 10100000 after zero padding.
        assertEquals(
                0b10100000,
                result[0] & 0xFF
        );
    }
}