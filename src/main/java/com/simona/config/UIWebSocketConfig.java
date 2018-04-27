//package com.simona.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class UIWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/livescore-websocket").setAllowedOrigins("*"). withSockJS();
//        registry.addEndpoint("/monitoring-websocket").setAllowedOrigins("*"). withSockJS();
//    }
//
//}