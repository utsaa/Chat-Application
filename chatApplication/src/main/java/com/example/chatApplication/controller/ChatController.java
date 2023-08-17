package com.example.chatApplication.controller;

import com.example.chatApplication.Queue.QueueService;
import com.example.chatApplication.model.ChatMessage;
import com.example.chatApplication.repositories.UsernameWsId;
import com.example.chatApplication.services.ChatApplicationService;
import com.example.chatApplication.services.NotificationService;
import com.example.chatApplication.services.UserService;
import com.example.chatApplication.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ChatController {
    Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    NotificationService notificationService;
    @Autowired
    ChatApplicationService chatApplicationService;

    @Autowired
    UserService userService;
    @Autowired
    QueueService queueService;
    @MessageMapping("/chat.register")
    @SendTo("topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor, final Principal principal){
        boolean val =userService.checkUserPresent(chatMessage.getSender());
        if (! val) throw new RuntimeException("user is not present");
        simpMessageHeaderAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        logger.info("Name in register {}", principal.getName());
        userService.addUserWsInMemory(chatMessage.getSender(), principal.getName());
        queueService.publish(chatMessage.getSender());

        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }


    @MessageMapping("/private-message/{toSender}")
    @SendToUser("/topic/private-messages")
    public ChatMessage getPrivateMessage(@Payload ChatMessage message,
                                         final Principal principal, @DestinationVariable String toSender) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendPrivateNotification(principal.getName());
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setSender(message.getSender());
        chatMessage.setContent("private message to "+ message.getContent()+" to "+principal.getName()+" to 2"+toSender);
        chatApplicationService.notifyUser(toSender, message);
        return chatMessage;

    }

    @MessageMapping("/private-message2")

    public ChatMessage getPrivateMessage2(@Payload ChatMessage message){
chatApplicationService.deleteOfflineUsers(message.getSender());
        return message;
    }

}
