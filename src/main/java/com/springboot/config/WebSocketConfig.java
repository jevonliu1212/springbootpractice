package com.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry arg0) {
		// TODO Auto-generated method stub
		arg0.addEndpoint("/endpoint1").withSockJS();//注册一个Stomp 协议的endpoint,并指定 SockJS协议。
	}

	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//配置消息代理(message broker)
        registry.enableSimpleBroker("/topic"); //广播式应配置一个/topic 消息代理
    }
}
