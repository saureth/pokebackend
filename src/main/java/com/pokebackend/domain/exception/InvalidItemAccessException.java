package com.pokebackend.domain.exception;

public class InvalidItemAccessException extends RuntimeException {
    public InvalidItemAccessException(String message) {
        super(message);
    }
}
