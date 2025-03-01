package com.thevivek2.example.common.example.refresh;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Data
@ConfigurationProperties(prefix = "thevivek2.testing.refreshable.beans")
public class RefreshableBeanConfig {

    private String use;
}
