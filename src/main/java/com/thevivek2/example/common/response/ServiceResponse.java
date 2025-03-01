package com.thevivek2.example.common.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceResponse<T> {
    private String status;
    private T data;

    private ServiceResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ServiceResponse<T> of(T data) {

        return new ServiceResponse<>("SUCCESS", data);
    }

    public static <T> ServiceResponse<T> success() {
        return new ServiceResponse<>("SUCCESS", null);
    }

    public static <T> ServiceResponse<T> success(T data) {
        return new ServiceResponse<>("SUCCESS", data);
    }

    public static <T> ServiceResponse<T> failure() {
        return new ServiceResponse<>("FAILURE", null);
    }

    public static <T> ServiceResponse<T> failure(T data) {
        return new ServiceResponse<>("FAILURE", data);
    }

    public static <T> ServiceResponse<T> dummy() {
        return new ServiceResponse<>("SUCCESS", null);
    }

    public static <T> ServiceResponse<T> dummy(T data) {
        return new ServiceResponse<>("SUCCESS", data);
    }

}
