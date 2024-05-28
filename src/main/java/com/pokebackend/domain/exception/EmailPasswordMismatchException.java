package com.pokebackend.domain.exception;

public class EmailPasswordMismatchException extends RuntimeException {
    public EmailPasswordMismatchException(String message) {
        super(message);
    }
}
