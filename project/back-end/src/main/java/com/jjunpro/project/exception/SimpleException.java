package com.jjunpro.project.exception;

public class SimpleException extends RuntimeException {

    public SimpleException(String message) {
        super(message);
    }

    public SimpleException(
            String message,
            Throwable cause
    ) {
        super(
                message,
                cause
        );
    }
}
