package com.thevivek2.example.common.config;

import com.thevivek2.example.common.exception.ApiException;
import com.thevivek2.example.common.exception.InvalidAccessException;
import com.thevivek2.example.common.exception.Violation;
import com.thevivek2.example.common.response.ServiceResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceConfig extends ResponseEntityExceptionHandler {

    @ApiResponse(responseCode = "400", description = "Error when request is made with invalid input",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Violation.class))})
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ServiceResponse<Violation>> handle(ApiException ex) {
        logger.error(ex);
        return ResponseEntity.badRequest().body(ServiceResponse.failure(Violation.of(ex)));
    }

    @ApiResponse(responseCode = "403", description = "Error when request is made with invalid input",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Violation.class))})
    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<ServiceResponse<Violation>> handleInvalidAccess(InvalidAccessException ex) {
        logger.error(ex);
        return ResponseEntity.status(403)
                .body(ServiceResponse.failure(Violation.of(ex)));
    }


    @Override
    @ApiResponse(responseCode = "400", description = "Error when request is made with invalid input",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Violation.class))})
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatusCode status,
                                                                         WebRequest request) {
        logger.error(ex);
        return ResponseEntity.badRequest().body(ServiceResponse.failure(Violation.of(ex)));
    }

    @ApiResponse(responseCode = "400", description = "Error when request is made with invalid input",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Violation.class))})
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        logger.error(ex);
        return ResponseEntity.badRequest().body(ServiceResponse.failure( Violation.of(ex)));
    }

    @ApiResponse(responseCode = "400", description = "Error when request is made with invalid input",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Violation.class))})
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        logger.error(ex);
        return ResponseEntity.badRequest().body(ServiceResponse.failure(Violation.of(ex)));
    }


    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Violation.class))})
    @MessageExceptionHandler
    public ResponseEntity<ServiceResponse<Violation>> handleException(Exception exception) {
        logger.error(exception);
        if (exception instanceof ApiException apiException) {
            return ResponseEntity.status(apiException.getStatusCode())
                    .body(ServiceResponse.failure(Violation.of(exception)));
        }
        return ResponseEntity.status(500).body(ServiceResponse.failure( Violation.of(exception)));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status, WebRequest request) {
        return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
    }
}
