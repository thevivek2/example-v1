package com.thevivek2.example.common.auth.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.thevivek2.example.common.util.HashUtil.getSha512;

@Component
@Log4j2
public class HashService {

    private final String authSecret;

    public HashService(@Value("${tws.auth.secret}") String authSecret) {
        this.authSecret = authSecret;
    }

    public String getHash(String target) {
        return getSha512(target, authSecret);
    }


}
