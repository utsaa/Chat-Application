package com.example.chatApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@SpringBootApplication
public class ChatApplication {
Logger logger= LoggerFactory.getLogger(ChatApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	@EventListener
	public void onDisconnectEvent(SessionDisconnectEvent event) {
		logger.debug("Client with username {} disconnected", event.getUser());
	}
}
