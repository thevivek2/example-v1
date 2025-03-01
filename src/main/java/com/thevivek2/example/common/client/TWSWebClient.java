package com.thevivek2.example.common.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thevivek2.example.common.exception.ApiException.of;
import static org.springframework.http.HttpStatus.*;

public interface TWSWebClient {

    Log LOGGER = LogFactory.getLog(TWSWebClient.class);

    default List<HttpStatusCode> successCodes() {
        return List.of(OK, CREATED, ACCEPTED, NOT_MODIFIED);
    }

    default HttpResponse<String> get(String url) {
        return get(url, new HashMap<>(), String.class);
    }

    default HttpResponse<String> get(String url, Map<String, String> headers) {
        return get(url, headers, String.class);
    }

    default <T> HttpResponse<T> get(String url, Class<T> t) {
        return get(url, new HashMap<>(), t);
    }

    default RestClient client() {
        return RestClient.builder().build();
    }

    /**
     * What this handles top of RestClient?
     * 1. In Failure case - Body is not Object that can be parsed to Provided type, so fallback as String
     * 2. Map ResponseEntity result to WebResponse format
     */
    default <T> HttpResponse<T> get(String url, Map<String, String> headers, Class<T> t) {
        LOGGER.trace("calling web url :" + url + " headers " + headers);
        final ResponseEntity<String> entity = client().get().uri(url, headers)
                .headers(x -> {
                    if (headers.isEmpty())
                        return;
                    var entries = headers.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        x.add(entry.getKey(), entry.getValue());
                    }
                })
                .retrieve().toEntity(String.class);
        HttpResponse<T> response = new HttpResponse<>();
        response.setStatusCode(entity.getStatusCode().value());
        response.setHeaders(entity.getHeaders().toSingleValueMap());
        response.setOriginalResult(entity.getBody());
        if (!successCodes().contains(HttpStatusCode.valueOf(entity.getStatusCode().value()))) {
            response.setFailure(true);
            response.setFailureMessage(entity.getBody());
            LOGGER.trace("Failure noticed :" + url + "headers " + "response is " + response.getFailureMessage());
            return response;
        }
        try {
            if (String.class == t) {
                response.setSuccessResult((T) entity.getBody());
                return response;
            }
            response.setSuccessResult(mapper().readValue(entity.getBody(), t));
        } catch (JsonProcessingException e) {
            throw of("Error while reading data", e);
        }
        response.setFailure(false);
        return response;
    }

    default String resolve(String uri, Map<String, String> uriVariables) {
        UriTemplate uriTemplate = new UriTemplate(uri);
        return uriTemplate.expand(uriVariables).toString();
    }

    ObjectMapper mapper();
}
