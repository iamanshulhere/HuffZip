package com.huffzip.model;

public class HuffmanNode implements Comparable<HuffmanNode> {

    /*
     * symbol:
     * 0 - 255 for a real byte/character.
     * -1 means this is an internal node.
     */
    private final int symbol;

    private final long frequency;

    private final HuffmanNode left;

    private final HuffmanNode right;

    /*
     * Used to make tree construction deterministic when
     * two nodes have the same frequency.
     */
    private final int minimumSymbol;

    // Constructor for a leaf node
    public HuffmanNode(int symbol, long frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
        this.minimumSymbol = symbol;
    }

    // Constructor for an internal node
    public HuffmanNode(
            long frequency,
            HuffmanNode left,
            HuffmanNode right
    ) {
        this.symbol = -1;
        this.frequency = frequency;
        this.left = left;
        this.right = right;

        this.minimumSymbol = Math.min(
                left.minimumSymbol,
                right.minimumSymbol
        );
    }

    public int getSymbol() {
        return symbol;
    }

    public long getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {

        int frequencyComparison =
                Long.compare(this.frequency, other.frequency);

        if (frequencyComparison != 0) {
            return frequencyComparison;
        }

        return Integer.compare(
                this.minimumSymbol,
                other.minimumSymbol
        );
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "symbol=" + symbol +
                ", frequency=" + frequency +
                ", leaf=" + isLeaf() +
                '}';
    }
}