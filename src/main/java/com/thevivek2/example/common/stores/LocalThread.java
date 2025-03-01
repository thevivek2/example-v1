package com.thevivek2.example.common.stores;

import com.thevivek2.example.common.exception.ApiException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class LocalThread {

    private static final Log logger = LogFactory.getLog(LocalThread.class);

    private static final InheritableThreadLocal<Map<String, String>> STORE = new InheritableThreadLocal<>() {
        @Override
        protected Map<String, String> childValue(Map<String, String> parentValue) {
            if (isNull(parentValue)) {
                return null;
            }
            return new HashMap<>(parentValue);
        }
    };

    public static void put(String key, String value) {
        try {
            logger.trace("Adding value to local thread scope variable: " + key);
            if (isNull(STORE.get())) {
                Map<String, String> kvStore = new HashMap<>();
                kvStore.put(key, value);
                STORE.set(kvStore);
                return;
            }
            STORE.get().put(key, value);
        } catch (Exception e) {
            throw ApiException.of("WTS-NULL", e);
        }
    }

    public static String get(String key) {
        logger.trace("Accessing from local thread scope variable: " + key);
        try {
            return STORE.get().get(key);
        } catch (Exception e) {
            throw ApiException.of("WTS-NULL", e);
        }
    }
}


