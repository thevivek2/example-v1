package com.thevivek2.example.common.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class WebRequestUtil {

    public static String getRequestUri(NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request != null) {
            return request.getRequestURI();
        }
        throw new IllegalArgumentException("Http request is expected in order to work");
    }

    public static HttpMethod getMethod(NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request != null) {
            return HttpMethod.valueOf(request.getMethod());
        }
        throw new IllegalArgumentException("Http request is expected in order to work");
    }

    public static String toQuery(Map<String, String> stickers) {

        if (stickers.isEmpty())
            return StringUtils.EMPTY;
        return stickers.keySet().stream()
                .map(x -> x + ":" + stickers.get(x))
                .collect(Collectors.joining(StringUtils.SPACE + " AND "));
    }

    public static String toExtendedQuery(String searchParam, String stickersStr) {
        if (Objects.isNull(searchParam))
            searchParam = stickersStr;
        else
            searchParam += " AND" + StringUtils.SPACE + stickersStr;
        return searchParam;
    }
}
