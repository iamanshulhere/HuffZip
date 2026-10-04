package com.huffzip.decoder;

import com.huffzip.encoder.HuffmanEncoder;
import com.huffzip.model.HuffmanNode;
import com.huffzip.model.HuffmanTree;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuffmanDecoderTest {

    @Test
    void shouldDecodeEncodedData()
            throws Exception {

        String original =
                "this is a huffman test";

        byte[] originalBytes =
                original.getBytes(
                        StandardCharsets.UTF_8
                );

        Map<Integer, Long> frequencies =
                new java.util.HashMap<>();

        for (byte value : originalBytes) {

            int unsigned =
                    value & 0xFF;

            frequencies.merge(
                    unsigned,
                    1L,
                    Long::sum
            );
        }

        HuffmanTree tree =
                new HuffmanTree(frequencies);

        Map<Integer, String> codes =
                tree.generateCodes();

        HuffmanNode root =
                tree.getRoot();

        ByteArrayOutputStream encoded =
                new ByteArrayOutputStream();

        HuffmanEncoder encoder =
                new HuffmanEncoder();

        encoder.encode(
                new ByteArrayInputStream(originalBytes),
                encoded,
                codes,
                root
        );

        ByteArrayOutputStream decoded =
                new ByteArrayOutputStream();

        HuffmanDecoder decoder =
                new HuffmanDecoder();

        decoder.decode(
                new ByteArrayInputStream(
                        encoded.toByteArray()
                ),
                decoded,
                root,
                originalBytes.length
        );

        String result =
                decoded.toString(
                        StandardCharsets.UTF_8
                );

        assertEquals(
                original,
                result
        );
    }
}