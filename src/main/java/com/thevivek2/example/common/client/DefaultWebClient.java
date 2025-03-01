package com.thevivek2.example.common.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("defaultWebClient")
public class DefaultWebClient implements TWSWebClient {

    private final ObjectMapper mapper;

    @Override
    public ObjectMapper mapper() {
        return mapper;
    }
}
