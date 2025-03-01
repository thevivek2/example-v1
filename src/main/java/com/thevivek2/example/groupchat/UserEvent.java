package com.thevivek2.example.groupchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevivek2.example.common.stores.LocalThread;
import com.thevivek2.example.common.track.RequestTrackerFilter;
import com.thevivek2.example.common.usecase.Optical;
import com.thevivek2.example.common.usecase.TheVivek2Event;
import com.thevivek2.example.common.usecase.TheVivek2Model;
import com.thevivek2.example.coroutine.NotBlockingVoid;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.thevivek2.example.common.track.RequestTrackerFilter.TRACK_REQUEST_GROUPING_ID;
import static com.thevivek2.example.common.track.RequestTrackerFilter.TRACK_REQUEST_UUID;

@Controller
@RestController
@SuppressWarnings({"rawtypes", "ClassEscapesDefinedScope"})
public class UserEvent {

    private static final Log logger = LogFactory.getLog(UserEvent.class);

    private final Map<String, Optical> allEventHandlers;
    private final OnMessageReceived errorHandler;
    private final ObjectMapper mapper;
    private final UserEventInternal block;

    @Autowired
    public UserEvent(List<Optical> allHandlers, OnMessageReceived onMessageReceived,
                     ObjectMapper mapper, UserEventInternal block) {
        allEventHandlers = allHandlers.stream().collect(Collectors.toMap(Optical::useCase, Function.identity()));
        this.errorHandler = onMessageReceived;
        this.mapper = mapper;
        this.block = block;
    }

    /**
     * Perfect input as messaging.
     * This enables sending messaging to server when connected via websocket.
     * @param headers
     * @param commandAndInput
     * @param user
     */
    @NotBlockingVoid
    @MessageMapping("/message")
    public void receive(@Headers Map<String, String> headers, @Payload String commandAndInput, Principal user) {
        letsTrack(headers);
        logger.info(LogMessage.of(() -> "an event occurred " + commandAndInput));
        try {
            TheVivek2Event event = mapper.readValue(commandAndInput, TheVivek2Model.class);
            if (!allEventHandlers.containsKey(event.getEvent())) {
                onInvalidCommand(commandAndInput, user);
                return;
            }
            handle(commandAndInput, user, event);
        } catch (Exception e) {
            onError(commandAndInput, user, e);
        }
    }

    private static void letsTrack(Map<String, String> headers) {
        MDC.put(TRACK_REQUEST_GROUPING_ID, headers.get("simpSessionId"));
        MDC.put(TRACK_REQUEST_UUID, RequestTrackerFilter.getRequestTrackId());
        LocalThread.put(TRACK_REQUEST_UUID, headers.get("simpSessionId"));
        LocalThread.put(TRACK_REQUEST_GROUPING_ID, RequestTrackerFilter.getTrackRequestGroupingId());
    }

    private void onError(String commandAndInput, Principal user, Exception e) {
        TheVivek2Model workStationModel = new TheVivek2Model();
        workStationModel.setCreatedBy(user.getName());
        workStationModel.setMessage("Oops!! We still failed to process and please report this. input "
                + commandAndInput + " ERROR ** : " + e.getMessage());
        errorHandler.execute(workStationModel);
        logger.error(e);
    }

    private void handle(String commandAndInput, Principal user, TheVivek2Event event) throws JsonProcessingException {
        Optical optical = allEventHandlers.get(event.getEvent());
        TheVivek2Model workStationModel = (TheVivek2Model) mapper.readValue(commandAndInput, optical.type());
        workStationModel.setCreatedBy(user.getName());
        workStationModel.setSource("WEBSOCKET");
        block.execute(optical, workStationModel);
    }

    private void onInvalidCommand(String commandAndInput, Principal user) {
        TheVivek2Model workStationModel = new TheVivek2Model();
        workStationModel.setCreatedBy(user.getName());
        workStationModel.setMessage(commandAndInput);
        errorHandler.execute(workStationModel);
    }

    /**
     * Perfect all events.
     * This provides all the events supported by System on messaging.
     * @return
     */
    @GetMapping("/internal/all-events")
    //Not return ServiceResponse to not make changes in app.js !!
    public Set<String> useCases() {
        return allEventHandlers.keySet();
    }
}

@SuppressWarnings("rawtypes")
@Component
@AllArgsConstructor
class UserEventInternal {

    private static final Log logger = LogFactory.getLog(UserEventInternal.class);

    public void execute(Optical handler, TheVivek2Model model) {
        logger.debug("Executing internal  " + handler.useCase() + " source :" + model.getSource());
        handler.execute(model);
    }

}
