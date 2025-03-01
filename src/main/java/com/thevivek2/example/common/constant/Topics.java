package com.thevivek2.example.common.constant;

import java.util.function.Function;


public interface Topics {

    Function<String, String> TOPIC_MESSAGES = x -> "/topic/message";
    Function<String, String> NEXT_TOPIC_USER = x -> "/topic/user/" + x + "222/message";

}
