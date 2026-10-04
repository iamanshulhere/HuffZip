package com.huffzip.exception;

public class DecompressionException
        extends RuntimeException {

    public DecompressionException(String message) {
        super(message);
    }

    public DecompressionException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}