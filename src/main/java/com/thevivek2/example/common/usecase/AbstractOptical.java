package com.thevivek2.example.common.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevivek2.example.common.stores.LocalThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.thevivek2.example.common.constant.Topics.TOPIC_MESSAGES;
import static com.thevivek2.example.common.track.RequestTrackerFilter.TRACK_REQUEST_GROUPING_ID;
import static com.thevivek2.example.common.track.RequestTrackerFilter.TRACK_REQUEST_UUID;
import static com.thevivek2.example.common.usecase.ExecutionStep.success;

public abstract class AbstractOptical<T extends TheVivek2Model> implements Optical<T> {

    private static final Log LOGGER = LogFactory.getLog(AbstractOptical.class);

    static final int SYSTEM_LATENCY_THRESHOLD_IN_MILLIS = 1000;

    @Autowired
    protected SimpMessagingTemplate template;

    @Autowired
    private ObjectMapper mapper;


    protected void preValidate(T data) {
    }

    protected T enrich(T data) {
        return data;
    }


    protected void postValidate(T data) {
    }

    protected T process(T data) {
        return data;
    }

    protected T save(T data) {
        return data;
    }

    protected void publish(T data) {
        for (Function<String, String> topic : topics()) {
            var destination = topic.apply(data.getCreatedBy());
            data.setDestinationChannel(destination);
            LOGGER.info("Sending message to destination: " + destination);
            template.convertAndSend(destination, getPayload(data), headers());
        }
    }

    protected void notify(T data) {
    }

    @Override
    public T execute(T data) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        preValidate(data);
        data.setExecuteStatus(0);
        data.addExecutionStep(success("*** Pre VALIDATION ***", "Just making sure this is a " +
                "valid data, have required data for this processing, dependent field, size, length, pattern etc."));
        T enrich = enrich(data);
        data = enrich != null ? enrich : data;


        data.addExecutionStep(success("*** ENRICH ***", "Enriching the data with additional " +
                "information, like adding some default values, setting some dependent fields, etc..."));
        postValidate(data);
        data.addExecutionStep(success("*** POST VALIDATION ***", "Just making sure this is a " +
                "just a further validation.."));

        T process = process(data);
        data = process != null ? process : data;
        data.addExecutionStep(success("*** PROCESS ***", "Processing the data, like calling " +
                "some external service, doing some calculation, adding or update intermediate object STATES etc..."));

        T save = save(data);
        data = save != null ? save : data;
        data.addExecutionStep(success("*** SAVE ***", "Saving the data to the database, like " +
                "inserting, updating, deleting, etc..."));
        publish(data);
        data.addExecutionStep(success("*** PUBLISH ***", "This could get interesting - it is " +
                "like a processor that reflects all of system data in Central like a NEWSPAPER or many TV channel"));
        notify(data);
        data.addExecutionStep(success("*** NOTIFY ***", "Notifying the registered listeners," +
                " sending an email, sending a message to the user or calling a webhook or etc..."));
        stopWatch.stop();
        if (stopWatch.getTotalTimeMillis() > SYSTEM_LATENCY_THRESHOLD_IN_MILLIS)
            notifyAdminForThresholdCross(data);
        data.setExecutionTime(stopWatch.getTotalTimeNanos());
        data.setExecuteStatus(7);
        return data;
    }

    protected List<Function<String, String>> topics() {
        return List.of(TOPIC_MESSAGES);
    }

    protected void notifyAdminForThresholdCross(T data) {
        new Thread(() -> execute(data)).start();
    }

    private String getPayload(T data) {
        try {
            return mapper.writeValueAsString(data.resource());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static @NotNull Map<String, Object> headers() {
        return Map.of(TRACK_REQUEST_UUID, LocalThread.get(TRACK_REQUEST_UUID),
                TRACK_REQUEST_GROUPING_ID, LocalThread.get(TRACK_REQUEST_GROUPING_ID));
    }

}
