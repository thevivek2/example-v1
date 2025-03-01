package com.thevivek2.example.common.exception;

public class DuplicateValueException extends RuntimeException{
    public DuplicateValueException(String message){
        super(message);
    }

    public DuplicateValueException(String message, Throwable cause){
        super(message, cause);
    }
}
