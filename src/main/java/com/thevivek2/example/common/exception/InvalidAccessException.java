package com.thevivek2.example.common.exception;

public class InvalidAccessException extends RuntimeException {

    public InvalidAccessException(String message) {
        super(message);
    }

    public InvalidAccessException(String message, Exception e) {
        super(message, e);
    }

    public static InvalidAccessException of(String message) {
        return new InvalidAccessException(message);
    }

    public static InvalidAccessException of(String message, Exception e) {
        return new InvalidAccessException(message, e);
    }



}
