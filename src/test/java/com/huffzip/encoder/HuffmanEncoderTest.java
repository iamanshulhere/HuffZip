package com.huffzip.encoder;

import com.huffzip.model.HuffmanNode;
import com.huffzip.model.HuffmanTree;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HuffmanEncoderTest {

    @Test
    void shouldEncodeText()
            throws Exception {

        byte[] input =
                "banana".getBytes(
                        StandardCharsets.UTF_8
                );

        Map<Integer, Long> frequencies =
                Map.of(
                        (int) 'b', 1L,
                        (int) 'a', 3L,
                        (int) 'n', 2L
                );

        HuffmanTree tree =
                new HuffmanTree(frequencies);

        Map<Integer, String> codes =
                tree.generateCodes();

        HuffmanNode root =
                tree.getRoot();

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        HuffmanEncoder encoder =
                new HuffmanEncoder();

        encoder.encode(
                new ByteArrayInputStream(input),
                output,
                codes,
                root
        );

        assertTrue(
                output.size() > 0
        );
    }
}