package com.example.chatApplication.Queue.handlers;

import com.example.chatApplication.model.ChatMessage;
import com.example.chatApplication.repositories.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class Subscriber {
    Logger logger = LoggerFactory.getLogger(Subscriber.class);
    private final Queue<ChatMessage> messages;
    private final String toSend;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public Queue<ChatMessage> getMessages() {
        return messages;
    }

    public String getToSend() {
        return toSend;
    }

    public UsernameWsId getUsernameWsId() {
        return usernameWsId;
    }

    public void setUsernameWsId(UsernameWsId usernameWsId) {
        this.usernameWsId = usernameWsId;
    }


    UsernameWsId usernameWsId;
    public Subscriber(String toSend,final SimpMessagingTemplate simpMessagingTemplate, final UsernameWsId usernameWsId) {
        this.toSend=toSend;

        this.simpMessagingTemplate = simpMessagingTemplate;
        this.usernameWsId = usernameWsId;
        messages=new LinkedList<>();
    }

    public synchronized void publish(ChatMessage message) {
        messages.add(message);
        logger.info(" User {} message Added {}",toSend, message);

    }


    public synchronized void publishMessages() {
        String id =usernameWsId.getRepo().get(toSend);
        for (ChatMessage message: messages) {
            simpMessagingTemplate.convertAndSendToUser(id, "/topic/private-messages", message);
            logger.info("User {} Message sent {}", toSend, message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (!usernameWsId.getOfflineUsers().contains(toSend)) {
            logger.info("Messages send to User {}", toSend);
            messages.clear();
        }
    }

    public synchronized boolean isMessageDeliverable() {
        logger.info("Username offliners {}, message size {} user {}",usernameWsId.getOfflineUsers(),messages.size(), toSend  );
        if (messages.size()==0) usernameWsId.getOfflineUsers().remove(toSend);
        return (usernameWsId.getOfflineUsers().contains(toSend)) && messages.size()>0;
    }
}
