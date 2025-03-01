package com.thevivek2.example.common.example.refresh;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@AllArgsConstructor
public class ConditionalRefreshableBeanFactory {

    /**
     * SPIKE
     * Expecting following if possible
     *
     * @Autowired
     * RefreshAware refreshAware
     *
     * @Service
     * @RefreshScope
     * @ConditionalOnProperty(name = "tws.testing.refreshable.beans.use", havingValue = "STATIC")
     * public class StaticContext implements RefreshAware
     *
     * @Service
     * @RefreshScope
     * @ConditionalOnProperty(name = "tws.testing.refreshable.beans.use", havingValue = "DYNAMIC")
     * public class DynamicContext implements RefreshAware
     *
     * POST http://localhost:8282/actuator/env { "name":tws.testing.refreshable.beans.use, "value":"STATIC"}
     * POST http://localhost:8282/actuator/refresh
     *
     */

    private final RefreshableBeanConfig config;
    private final DynamicContext context;
    private final StaticContext staticContext;

    public RefreshAware getRefreshAware() {
        if ("DYNAMIC".equals(config.getUse()))
            return context;
        if ("STATIC".equals(config.getUse()))
            return staticContext;
        throw new IllegalArgumentException("INVALID CASE");
    }

    @PreDestroy
    void preDestroy() {
        context.close();
        staticContext.close();
    }
}
