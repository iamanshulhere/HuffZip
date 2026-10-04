package com.huffzip.file;

import com.huffzip.util.Constants;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HuffmanFileWriter {

    public void writeHeader(
            OutputStream outputStream,
            long originalSize,
            Map<Integer, Long> frequencies
    ) throws IOException {

        DataOutputStream dataOutput =
                new DataOutputStream(outputStream);

        // Magic header: HUF1
        dataOutput.writeInt(
                Constants.MAGIC_HEADER
        );

        // Original file size
        dataOutput.writeLong(originalSize);

        // Number of unique symbols
        dataOutput.writeInt(
                frequencies.size()
        );

        // Frequency table
        for (Map.Entry<Integer, Long> entry :
                frequencies.entrySet()) {

            dataOutput.writeByte(
                    entry.getKey()
            );

            dataOutput.writeLong(
                    entry.getValue()
            );
        }

        dataOutput.flush();
    }
}