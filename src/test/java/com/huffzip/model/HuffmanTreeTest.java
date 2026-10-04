package com.huffzip.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTreeTest {

    @Test
    void shouldBuildTreeAndGenerateCodes() {

        Map<Integer, Long> frequencies =
                Map.of(
                        (int) 'a', 5L,
                        (int) 'b', 3L,
                        (int) 'c', 2L
                );

        HuffmanTree tree =
                new HuffmanTree(frequencies);

        assertNotNull(tree.getRoot());

        Map<Integer, String> codes =
                tree.generateCodes();

        assertEquals(3, codes.size());

        assertTrue(
                codes.containsKey((int) 'a')
        );

        assertTrue(
                codes.containsKey((int) 'b')
        );

        assertTrue(
                codes.containsKey((int) 'c')
        );

        // No code should be empty for a multi-symbol tree.
        for (String code : codes.values()) {
            assertFalse(code.isEmpty());
        }
    }

    @Test
    void shouldHandleSingleSymbol() {

        Map<Integer, Long> frequencies =
                Map.of(
                        (int) 'a',
                        10L
                );

        HuffmanTree tree =
                new HuffmanTree(frequencies);

        assertNotNull(tree.getRoot());

        assertTrue(
                tree.getRoot().isLeaf()
        );

        Map<Integer, String> codes =
                tree.generateCodes();

        assertEquals(
                "0",
                codes.get((int) 'a')
        );
    }
}