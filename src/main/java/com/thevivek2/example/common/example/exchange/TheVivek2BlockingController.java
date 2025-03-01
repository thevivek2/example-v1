package com.thevivek2.example.common.example.exchange;

import com.thevivek2.example.common.client.TWSWebClient;
import com.thevivek2.example.common.client.HttpResponse;
import com.thevivek2.example.common.config.TheVivek2TestConfig;
import com.thevivek2.example.common.exception.ApiException;
import com.thevivek2.example.common.response.ServiceResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/web-client")
@Log4j2
@RefreshScope
public class TheVivek2BlockingController {

    private final TWSWebClient restClient;
    private final TheVivek2TestConfig testConfig;


    public TheVivek2BlockingController(TheVivek2TestConfig testConfig,
                                       TWSWebClient restClient) {
        this.testConfig = testConfig;
        this.restClient = restClient;
    }

    /**
     * Perfect wrapper for RestClient.
     *
     * @return
     */
    @GetMapping
    public ServiceResponse<String> doGet() {
        final HttpResponse<String> response = restClient.get(testConfig.getExternalServiceUrl());
        if (response.isFailure())
            throw ApiException.of(response.getFailureMessage());
        return ServiceResponse.success(response.getSuccessResult());
    }

    @GetMapping("/json")
    public ServiceResponse<Map<String, String>> doGetJson() {
        final HttpResponse<HashMap> response = restClient.get(testConfig.getExternalServiceUrl(), HashMap.class);
        if (response.isFailure())
            throw ApiException.of(response.getFailureMessage());
        return ServiceResponse.of(response.getSuccessResult());
    }

    @PostConstruct
    void logExternalServiceUrl() {
        log.info("Using external url {} , make sure this url " +
                "response slow and hit /api/v2/web-client which is blocking in nature", testConfig.getExternalServiceUrl());
    }
}
