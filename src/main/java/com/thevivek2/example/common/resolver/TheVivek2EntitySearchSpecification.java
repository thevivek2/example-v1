package com.thevivek2.example.common.resolver;

import com.sipios.springsearch.SpecificationsBuilder;
import com.sipios.springsearch.anotation.SearchSpec;
import com.thevivek2.example.common.anotation.Strict;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@AllArgsConstructor
public class TheVivek2EntitySearchSpecification implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Specification.class
                && parameter.hasParameterAnnotation(SearchSpec.class)
                && !parameter.hasParameterAnnotation(Strict.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        var def = parameter.getParameterAnnotation(SearchSpec.class);
        return buildSpecification(parameter.getGenericParameterType().getClass(),
                webRequest.getParameter(def.searchParam()), def);
    }


    protected  <T> Specification<T> buildSpecification(Class<T> specClass,
                                                    String search,
                                                    SearchSpec searchSpecAnnotation) {
        if (Objects.isNull(search) || search.isEmpty()) {
            return null;
        }
        SpecificationsBuilder<T> specBuilder = new SpecificationsBuilder<>(searchSpecAnnotation);
        return specBuilder.withSearch(search).build();
    }
}
