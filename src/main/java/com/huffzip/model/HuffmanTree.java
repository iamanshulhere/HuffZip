package com.huffzip.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    private final HuffmanNode root;

    public HuffmanTree(Map<Integer, Long> frequencies) {
        this.root = buildTree(frequencies);
    }

    private HuffmanNode buildTree(
            Map<Integer, Long> frequencies
    ) {

        if (frequencies == null || frequencies.isEmpty()) {
            return null;
        }

        PriorityQueue<HuffmanNode> minHeap =
                new PriorityQueue<>();

        // Create leaf nodes
        for (Map.Entry<Integer, Long> entry :
                frequencies.entrySet()) {

            minHeap.add(
                    new HuffmanNode(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        // Special case:
        // File contains only one unique byte.
        if (minHeap.size() == 1) {
            return minHeap.poll();
        }

        // Build Huffman tree
        while (minHeap.size() > 1) {

            HuffmanNode left = minHeap.poll();

            HuffmanNode right = minHeap.poll();

            HuffmanNode parent =
                    new HuffmanNode(
                            left.getFrequency()
                                    + right.getFrequency(),
                            left,
                            right
                    );

            minHeap.add(parent);
        }

        return minHeap.poll();
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public Map<Integer, String> generateCodes() {

        Map<Integer, String> codes = new HashMap<>();

        if (root == null) {
            return codes;
        }

        // Single-symbol file
        if (root.isLeaf()) {
            codes.put(root.getSymbol(), "0");
            return codes;
        }

        generateCodesRecursive(
                root,
                "",
                codes
        );

        return codes;
    }

    private void generateCodesRecursive(
            HuffmanNode node,
            String code,
            Map<Integer, String> codes
    ) {

        if (node == null) {
            return;
        }

        if (node.isLeaf()) {

            codes.put(
                    node.getSymbol(),
                    code
            );

            return;
        }

        generateCodesRecursive(
                node.getLeft(),
                code + "0",
                codes
        );

        generateCodesRecursive(
                node.getRight(),
                code + "1",
                codes
        );
    }
}