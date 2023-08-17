package com.example.chatApplication.handlers;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    public final Logger logger= LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        try {
            logger.info("Request.body {}",request.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final String randomId = UUID.randomUUID().toString();
        logger.info("User with ID '{}' opened the page", randomId);

        return new UserPrincipal(randomId);
    }
}
