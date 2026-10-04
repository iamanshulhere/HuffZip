package com.huffzip.decoder;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitReaderTest {

    @Test
    void shouldReadBitsCorrectly()
            throws Exception {

        byte[] data = {
                (byte) 0b10110010
        };

        BitReader reader =
                new BitReader(
                        new ByteArrayInputStream(data)
                );

        assertEquals(1, reader.readBit());
        assertEquals(0, reader.readBit());
        assertEquals(1, reader.readBit());
        assertEquals(1, reader.readBit());
        assertEquals(0, reader.readBit());
        assertEquals(0, reader.readBit());
        assertEquals(1, reader.readBit());
        assertEquals(0, reader.readBit());

        assertEquals(-1, reader.readBit());
    }
}