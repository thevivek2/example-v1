package com.thevivek2.example.common.resolver;

import com.sipios.springsearch.anotation.SearchSpec;
import com.thevivek2.example.common.anotation.Strict;
import com.thevivek2.example.common.anotation.Stricter;
import com.thevivek2.example.common.anotation.StricterKey;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.thevivek2.example.common.resolver.WebRequestUtil.getMethod;
import static com.thevivek2.example.common.resolver.WebRequestUtil.getRequestUri;

@Component
@AllArgsConstructor
@Log4j2
public class TheVivek2EntityStrictSearchSpecification extends TheVivek2EntitySearchSpecification {

    private final Stricter stricter;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Specification.class
                && parameter.hasParameterAnnotation(SearchSpec.class)
                && parameter.hasParameterAnnotation(Strict.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        var def = parameter.getParameterAnnotation(SearchSpec.class);
        String originalSearch = webRequest.getParameter(def.searchParam());
        log.info("search parameters {}", originalSearch);
        var stickers = stricter.getFilter(StricterKey.of(getRequestUri(webRequest), getMethod(webRequest)));
        String stickersStr = WebRequestUtil.toQuery(stickers);
        String extendedSearch = WebRequestUtil.toExtendedQuery(originalSearch, stickersStr);
        log.info("search parameters after stickers {}", extendedSearch);
        return buildSpecification(parameter.getGenericParameterType().getClass(), extendedSearch, def);
    }


}
