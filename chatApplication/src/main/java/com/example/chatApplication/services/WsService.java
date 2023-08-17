package com.example.chatApplication.services;

import com.example.chatApplication.Queue.QueueService;
import com.example.chatApplication.dto.GroupChat;
import com.example.chatApplication.dto.GroupMessages;
import com.example.chatApplication.dto.Message;
import com.example.chatApplication.dto.User;
import com.example.chatApplication.model.ChatMessage;
import com.example.chatApplication.repositories.GroupChatRepository;
import com.example.chatApplication.repositories.MessageRepository;
import com.example.chatApplication.repositories.UserRepository;
import com.example.chatApplication.repositories.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WsService {

    Logger logger= LoggerFactory.getLogger(WsService.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    private final UsernameWsId usernameWsId;

    private final QueueService queueService;

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public WsService(SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService, UsernameWsId usernameWsId, QueueService queueService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
        this.usernameWsId = usernameWsId;
        this.queueService = queueService;
    }

    public void notifyFrontend(final ChatMessage message){
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setContent(message.getContent());
        chatMessage.setSender(message.getSender());
        notificationService.sendGlobalNotification();
        simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    public void notifyUser(final String groupId, final ChatMessage message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GroupChat chat = groupChatRepository.findById(groupId).orElse(null);
        if (chat == null) throw new RuntimeException("No such chat found");
        logger.info("Chat found {}", chat);
        List<User> users = userRepository.findUserByName(message.getSender());
        if (users.size()==0) throw  new RuntimeException("User is not registered");
        User user=users.get(0);
        if (!chat.getUsers().contains(user)){
            throw new RuntimeException("User not present in chat user "+ user);
        }
        logger.info("User found {}", user);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(message.getContent());
        chatMessage.setSender(message.getSender());
        users=chat.getUsers().stream().toList();
        logger.info( "The users are {}", users);
        for (User u : users) {
            String id = usernameWsId.getRepo().get(u.getName());
            logger.info("The username user {} WsId repo {} id {}", u , usernameWsId.getRepo(), id);
            if (id==null){
                logger.info("The id is null for user {}", u);
                continue;
            }
                usernameWsId.getOfflineUsers().add(u.getName());

            notificationService.sendPrivateNotification(id);
            queueService.publishMessages(u.getName(),chatMessage);
        }
        GroupMessages groupMessages=chat.getGroupMessages();


        Message message1=new Message(user, message.getContent());
        groupMessages.getUserMessages().add(message1);
        messageRepository.save(message1);
        chat.setGroupMessages(groupMessages);
        groupChatRepository.save(chat);

    }
}
