package com.thevivek2.example.common.example.refresh;

import com.thevivek2.example.common.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example/refresh")
@AllArgsConstructor
public class RefreshExampleController {

    private final ConditionalRefreshableBeanFactory factory;

    /**
     * Perfect refresh example.
     * This api has a factory implementation that switches RefreshAware Implementation at runtime.
     * When configuration properties are updated using actuator/env POST and /actuator/refresh is called -
     * it uses new bean instance with configuration updated.
     * @return string
     */
    @GetMapping
    public ServiceResponse<String> refresh() {
        return ServiceResponse.of(factory.getRefreshAware().refresh());
    }
}
