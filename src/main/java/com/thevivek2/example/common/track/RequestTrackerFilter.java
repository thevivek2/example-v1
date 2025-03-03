package com.thevivek2.example.common.track;

import com.thevivek2.example.common.stores.LocalThread;
import com.thevivek2.example.common.util.TheVivek2HttpServletRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Service
@AllArgsConstructor
@Order(LOWEST_PRECEDENCE)
public class RequestTrackerFilter extends OncePerRequestFilter {

    private static final Log logger = LogFactory.getLog(RequestTrackerFilter.class);

    public static final String TRACK_EXECUTION_TIME = "TRACK-Execution-Time";
    public static final String TRACK_REQUEST_UUID = "TRACK-Request-Uuid";
    public static final String TRACK_REQUEST_GROUPING_ID = "TRACK-Request-Grouping-ID";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var watch = new StopWatch();
        var customWrappedRequest = new TheVivek2HttpServletRequestWrapper(request);
        var uuid = getRequestTrackId();
        MDC.put(TRACK_REQUEST_UUID, uuid);
        MDC.put(TRACK_REQUEST_GROUPING_ID, getTrackRequestGroupingId());
        LocalThread.put(TRACK_REQUEST_UUID, uuid);
        LocalThread.put(TRACK_REQUEST_GROUPING_ID, getTrackRequestGroupingId());

        watch.start();
        filterChain.doFilter(customWrappedRequest, response);
        watch.stop();

        response.setHeader(TRACK_REQUEST_UUID, uuid);
        response.setHeader(TRACK_REQUEST_GROUPING_ID, getTrackRequestGroupingId());
        response.setHeader(TRACK_EXECUTION_TIME, String.valueOf(watch.getTotalTimeNanos()));
        var message = String.format("Request to %s %s returned status %d in %d ns",
                request.getMethod(), request.getRequestURI(), response.getStatus(), watch.getTotalTimeNanos());
        logger.trace(message);
    }

    public static String getRequestTrackId() {
        return UUID.randomUUID().toString();
    }

    public static String getTrackRequestGroupingId() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
