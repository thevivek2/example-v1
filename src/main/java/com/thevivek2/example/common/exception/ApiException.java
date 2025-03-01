package com.thevivek2.example.common.exception;

import lombok.Getter;

public class ApiException extends RuntimeException {

    @Getter
    private int statusCode = 400;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Exception e) {
        super(message, e);
    }

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static ApiException of(String message) {
        return new ApiException(message);
    }

    public static ApiException of(String message, Exception e) {
        e.printStackTrace();
        return new ApiException(message, e);
    }

    public static ApiException noAccess(String message) {
        return new ApiException(message, 403);
    }



}
