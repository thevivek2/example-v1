package com.thevivek2.example.common.config;

import com.thevivek2.example.common.exception.ApiException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static java.util.Objects.requireNonNull;
import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Log logger = LogFactory.getLog(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        logger.info("application destination /app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v2/ws-connect");
    }

    /**
     * https://docs.spring.io/spring-framework/reference/web/websocket/stomp/authentication-token-based.html
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                final MessageHeaders messageHeaders = accessor.getMessageHeaders();
                if (CONNECT.equals(accessor.getCommand())) {
                    logger.debug("User is connected. SessionId is " + messageHeaders.get("simpSessionId"));
                }
                if (SUBSCRIBE.equals(accessor.getCommand())) {
                    Authentication simpUser = (Authentication) accessor.getMessageHeaders().get("simpUser");
                    String destination = accessor.getDestination();
                    canSubscribe(requireNonNull(simpUser).getName(), requireNonNull(destination));
                    accessor.setUser(simpUser);
                    logger.debug("preSend().User is asked to subscribe this messaging channel.SessionId is " +
                            "" + messageHeaders.get("simpSessionId"));
                }
                return message;
            }
        });
    }

    private void canSubscribe(String name, String destination) {
        logger.info("checking if destination " + destination + " can be subscribed by user  " + name);
        if (destination.startsWith("/topic/user/") && !destination.startsWith("/topic/user/" + name + "/")) {
            logger.info("User is trying to subscribe " + destination + "not allowed ");
            throw ApiException.of("You are trying to subscribe destination that is not allowed");
        }
    }

}
