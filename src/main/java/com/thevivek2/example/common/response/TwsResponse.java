package com.thevivek2.example.common.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
public class TwsResponse {

    public static ResponseEntity<ServiceResponse> success(ServiceResponse entity) {
        return ResponseEntity.ok().body(entity);
    }

    public static ResponseEntity<ServiceResponse> success(HttpStatus status, ServiceResponse entity) {
        return ResponseEntity.status(status).body(entity);
    }

    public static ResponseEntity<ServiceResponse> failure(HttpStatus status, ServiceResponse entity) {
        return ResponseEntity.status(status).body(entity);
    }

    public static ResponseEntity<ServiceResponse> badRequest(ServiceResponse entity) {
        return ResponseEntity.badRequest().body(entity);
    }

    public static ResponseEntity<ServiceResponse> unauthorized(ServiceResponse entity) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(entity);
    }

    public static ResponseEntity<ServiceResponse> forbidden(ServiceResponse entity) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(entity);
    }

    public static ResponseEntity<ServiceResponse> notFound(ServiceResponse entity) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entity);
    }

    public static ResponseEntity<ServiceResponse> internalError(ServiceResponse entity) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(entity);
    }

    public static ResponseEntity<ServiceResponse> conflict(ServiceResponse entity) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(entity);
    }

}
