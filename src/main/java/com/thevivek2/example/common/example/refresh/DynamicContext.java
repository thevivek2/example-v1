package com.thevivek2.example.common.example.refresh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RefreshScope
public class DynamicContext implements RefreshAware {

    private static final Log logger = LogFactory.getLog(DynamicContext.class);

    private final RefreshableBeanConfig config;

    @Autowired
    public DynamicContext(RefreshableBeanConfig config) {
        this.config = config;
        logger.info("Dynamic context initialized at " + LocalDateTime.now());
    }

    @Override
    public String refresh() {
        return "DynamicContext.get()" + config.getUse();
    }

    @Override
    public void close() {
        logger.info("DynamicContext.close() " + LocalDateTime.now());
    }
}
