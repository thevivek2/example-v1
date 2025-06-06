package com.thevivek2.example.groupchat;

import com.thevivek2.example.common.constant.Topics;
import com.thevivek2.example.common.usecase.AbstractOptical;
import com.thevivek2.example.common.usecase.TheVivek2Model;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class OnMessageReceived extends AbstractOptical<TheVivek2Model> {

    @Override
    protected TheVivek2Model process(TheVivek2Model data) {
        data.setExecuteStatus(0);
        data.setEvent("CHATBOT");
        data.setMessage(data.getMessage());
        return data;
    }

    @Override
    protected List<Function<String, String>> topics() {
        return List.of(Topics.TOPIC_MESSAGES);
    }

    @Override
    public String useCase() {
        return "ON_MESSAGE_RECEIVED";
    }
}
