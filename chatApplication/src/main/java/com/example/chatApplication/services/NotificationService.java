package com.example.chatApplication.services;

import com.example.chatApplication.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    public void sendGlobalNotification() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent("Global notification");
        simpMessagingTemplate.convertAndSend("/topic/global-notification", chatMessage);
    }

    public void sendPrivateNotification(String userId){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent("private notification");
        simpMessagingTemplate.convertAndSendToUser(userId,"/topic/private-notification", chatMessage);

    }
}
