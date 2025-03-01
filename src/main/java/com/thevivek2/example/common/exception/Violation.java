package com.thevivek2.example.common.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Data
@Builder
@Slf4j
public class Violation {

    private static final Log logger = LogFactory.getLog(Violation.class);
    private String message;

    public static Violation of(Exception e) {
        logger.warn(e);
        return Violation.builder().message(e.getMessage()).build();
    }
}
