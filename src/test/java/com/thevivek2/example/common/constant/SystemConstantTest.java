package com.thevivek2.example.common.constant;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thevivek2.example.common.constant.SystemConstant.DATE_FORMATTER;
import static com.thevivek2.example.common.constant.SystemConstant.DATE_TIME_FORMATTER;
import static com.thevivek2.example.common.track.RequestTrackerFilter.TRACK_REQUEST_GROUPING_ID;
import static com.thevivek2.example.common.track.RequestTrackerFilter.TRACK_REQUEST_UUID;
import static org.assertj.core.api.Assertions.assertThat;

class SystemConstantTest {

    @Test
    void dateFormat() {
        assertThat(DATE_FORMATTER.parse("2020-10-10")).isNotNull();
    }

    @Test
    void dateTimeFormat() {
        assertThat(DATE_TIME_FORMATTER.parse("2020-10-10 10:10:10")).isNotNull();
    }


    @Test
    void hello() {
        final Map<String, String> trackRequestUuid = Map.of(TRACK_REQUEST_UUID, "A",
                TRACK_REQUEST_GROUPING_ID, "B");
        assertThat(trackRequestUuid).hasSize(2);
    }
}