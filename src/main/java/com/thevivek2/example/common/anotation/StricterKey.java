package com.thevivek2.example.common.anotation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Log4j2
public class StricterKey {

    private String api;
    private HttpMethod method;

    public static StricterKey of(String api, HttpMethod method) {
        log.info("providing stricter key for api {} and method {}", api, method);
        return new StricterKey(api, method);
    }

}
