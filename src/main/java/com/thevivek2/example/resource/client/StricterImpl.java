package com.thevivek2.example.resource.client;

import com.thevivek2.example.common.anotation.Stricter;
import com.thevivek2.example.common.anotation.StricterKey;
import com.thevivek2.example.common.auth.TheVivek2AuthService;
import com.thevivek2.example.common.constant.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.thevivek2.example.common.anotation.StricterKey.of;
import static com.thevivek2.example.resource.client.ClientAPIs.CLIENT_API_STRICT_EXAMPLE;
import static org.springframework.http.HttpMethod.GET;

@Service
@Log4j2
public class StricterImpl implements Stricter {

    private final TheVivek2AuthService authService;

    private final Map<StricterKey, Map<String, Supplier<String>>> filters;

    public StricterImpl(TheVivek2AuthService authService) {
        this.authService = authService;
        this.filters = new LinkedHashMap<>();
        initStickersProviders();
    }

    @Override
    public Map<String, String> getFilter(StricterKey key) {
        if (!filters.containsKey(key))
            return new HashMap<>();
        var providedFilter = filters.get(key);
        var computed = new LinkedHashMap<String, String>();
        providedFilter.forEach((k, v) -> computed.put(k, v.get()));
        log.info("provided additional filters to apply for key {} and filters {}", key, computed);
        return computed;
    }

    private void initStickersProviders() {
        filters.put(of(CLIENT_API_STRICT_EXAMPLE, GET), Map.of("clientType.activeStatus", (() -> Constants.ACTIVE)));
    }


}
