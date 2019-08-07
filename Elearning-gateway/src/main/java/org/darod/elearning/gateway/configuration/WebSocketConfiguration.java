package org.darod.elearning.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/8/2 0002 12:44
 */
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//
//    @Value("${org.darod.elearning.gateway.rabbitmqHost}")
//    private String rabbitmqHost;
//    @Value("${org.darod.elearning.gateway.rabbitmqUser}")
//    private String rabbitmqUser;
//    @Value("${org.darod.elearning.gateway.rabbitmqPassword}")
//    private String rabbitmqPassword;
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/live_socket").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.setApplicationDestinationPrefixes("/app")
//                .enableStompBrokerRelay("/topic", "/queue")
//                .setRelayHost(rabbitmqHost)
//                .setRelayPort(61613)
//                .setClientLogin(rabbitmqUser)
//                .setClientPasscode(rabbitmqPassword)
//                .setSystemLogin(rabbitmqUser)
//                .setSystemPasscode(rabbitmqPassword)
//                .setSystemHeartbeatSendInterval(5000)
//                .setSystemHeartbeatReceiveInterval(4000);
////        config.enableSimpleBroker("/topic");
////        config.setApplicationDestinationPrefixes("/app");
//
//    }
//}
