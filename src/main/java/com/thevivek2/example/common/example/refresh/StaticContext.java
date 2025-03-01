package com.thevivek2.example.common.example.refresh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RefreshScope
public class StaticContext implements RefreshAware {

    private static final Log logger = LogFactory.getLog(StaticContext.class);

    private final RefreshableBeanConfig config;

    @Autowired
    public StaticContext(RefreshableBeanConfig config) {
        this.config = config;
        logger.info("Static context initialized at " + LocalDateTime.now());
    }

    @Override
    public String refresh() {
        return "StaticContext.get()" + config.getUse();
    }

    @Override
    public void close() {
        logger.info("StaticContext.close() " + LocalDateTime.now());
    }
}
