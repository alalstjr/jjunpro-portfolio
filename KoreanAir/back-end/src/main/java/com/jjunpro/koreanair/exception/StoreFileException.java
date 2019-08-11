package com.jjunpro.koreanair.exception;

public class StoreFileException extends RuntimeException {
    public StoreFileException(String message) {
        super(message);
    }

    public StoreFileException(String message, Throwable cause) {
        super(message, cause);
    }
}