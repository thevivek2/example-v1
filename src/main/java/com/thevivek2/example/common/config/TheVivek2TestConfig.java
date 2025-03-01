package com.thevivek2.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "thevivek2.testing")
@RefreshScope
public class TheVivek2TestConfig {

    private String externalServiceUrl;

    public String getExternalServiceUrl() {
        return externalServiceUrl;
    }

    public void setExternalServiceUrl(String externalServiceUrl) {
        this.externalServiceUrl = externalServiceUrl;
    }

}
