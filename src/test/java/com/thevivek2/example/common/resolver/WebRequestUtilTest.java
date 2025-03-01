package com.thevivek2.example.common.resolver;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.thevivek2.example.common.resolver.WebRequestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

class WebRequestUtilTest {

    @Test
    void toQuery() {
        Map<String, String> map = Map.of("displayName", "JIWAN*");
        assertThat(WebRequestUtil.toQuery(map)).isEqualTo("displayName:JIWAN*");
    }

    @Test
    void toQueryMultiple() {
        var restriction = new LinkedHashMap<String, String>();
        restriction.put("displayName", "JIWAN*");
        restriction.put("userName", "thevivek2");
        assertThat(WebRequestUtil.toQuery(restriction))
                .isEqualTo("displayName:JIWAN*  AND userName:thevivek2");
    }

    @Test
    void toExtendedQueryWhenOriginalSearchIsNull() {
        assertThat(toExtendedQuery(null, "displayName:JIWAN*"))
                .isEqualTo("displayName:JIWAN*");
    }

    @Test
    void toExtendedQueryWhenOriginalSearchIsPresent() {
        assertThat(toExtendedQuery("displayName:JIWAN*", "userName:thevivek2"))
                .isEqualTo("displayName:JIWAN* AND userName:thevivek2");
    }
}