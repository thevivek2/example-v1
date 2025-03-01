package com.thevivek2.example.common.client;

import lombok.Data;

import java.util.Map;

@Data
public class HttpResponse<T> {

    private boolean failure;
    private String failureMessage;
    private String originalResult;
    private T successResult;
    private int statusCode;
    private Map<String, String> headers;

}
