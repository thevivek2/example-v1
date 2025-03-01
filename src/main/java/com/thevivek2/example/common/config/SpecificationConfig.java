package com.thevivek2.example.common.config;

import com.thevivek2.example.common.resolver.TheVivek2EntitySearchSpecification;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class SpecificationConfig implements WebMvcConfigurer {

    private final List<TheVivek2EntitySearchSpecification> specifications;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(specifications);
    }


}


