package com.huffzip.util;

public class CompressionStatistics {

    private final long originalSize;

    private final long compressedSize;

    public CompressionStatistics(
            long originalSize,
            long compressedSize
    ) {
        this.originalSize = originalSize;
        this.compressedSize = compressedSize;
    }

    public long getOriginalSize() {
        return originalSize;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public double getCompressionRatio() {

        if (originalSize == 0) {
            return 0.0;
        }

        return ((double) compressedSize
                / originalSize) * 100.0;
    }

    public double getSpaceSaved() {

        if (originalSize == 0) {
            return 0.0;
        }

        return ((double) (originalSize - compressedSize)
                / originalSize) * 100.0;
    }

    public void print() {

        System.out.println();
        System.out.println("========== HuffZip Statistics ==========");

        System.out.println(
                "Original Size   : "
                        + formatBytes(originalSize)
        );

        System.out.println(
                "Compressed Size : "
                        + formatBytes(compressedSize)
        );

        System.out.printf(
                "Compression Ratio: %.2f%%%n",
                getCompressionRatio()
        );

        System.out.printf(
                "Space Saved      : %.2f%%%n",
                getSpaceSaved()
        );

        System.out.println(
                "========================================="
        );
    }

    private String formatBytes(long bytes) {

        if (bytes < 1024) {
            return bytes + " B";
        }

        if (bytes < 1024 * 1024) {
            return String.format(
                    "%.2f KB",
                    bytes / 1024.0
            );
        }

        if (bytes < 1024 * 1024 * 1024) {
            return String.format(
                    "%.2f MB",
                    bytes / (1024.0 * 1024.0)
            );
        }

        return String.format(
                "%.2f GB",
                bytes / (1024.0 * 1024.0 * 1024.0)
        );
    }
}