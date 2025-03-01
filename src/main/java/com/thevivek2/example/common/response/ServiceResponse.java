package com.thevivek2.example.common.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceResponse<T> {
    private String status;
    private T data;
    private String level;
    private String message;

    private ServiceResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ServiceResponse<T> of(T data) {
        return new ServiceResponse<>("SUCCESS", null, data);
    }

    public static <T> ServiceResponse<T> success(String message) {
        return new ServiceResponse<>("SUCCESS", message, null);
    }

    public static <T> ServiceResponse<T> success(String message, T data) {
        return new ServiceResponse<>("SUCCESS", message, data);
    }

    public static <T> ServiceResponse<T> failure(String message) {
        return new ServiceResponse<>("FAILURE", message, null);
    }

    public static <T> ServiceResponse<T> failure(String message, T data) {
        return new ServiceResponse<>("FAILURE", message, data);
    }

    public static <T> ServiceResponse<T> dummy() {
        return new ServiceResponse<>("SUCCESS", "This is dummy message", null);
    }

    public static <T> ServiceResponse<T> dummy(T data) {
        return new ServiceResponse<>("SUCCESS", "This is dummy message", data);
    }

}
