package com.example.chatApplication.services;

import com.example.chatApplication.Queue.QueueService;
import com.example.chatApplication.model.ChatMessage;
import com.example.chatApplication.repositories.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatApplicationService {

    Logger logger= LoggerFactory.getLogger(ChatApplicationService.class);
//    private final SimpMessagingTemplate simpMessagingTemplate;


    private final NotificationService notificationService;

    private final UsernameWsId usernameWsId;
    private final QueueService queueService;

    @Autowired
    public ChatApplicationService( NotificationService notificationService, UsernameWsId usernameWsId, QueueService queueService) {

        this.notificationService = notificationService;
        this.usernameWsId = usernameWsId;


        this.queueService = queueService;
    }

    public void notifyUser(final String toSend, final ChatMessage message) {
            String id = usernameWsId.getRepo().get(toSend);
            logger.info("The username user {} WsId repo {} id {}", toSend , usernameWsId.getRepo(), id);
            if (id==null){
                logger.info("The id is null for user {}", toSend);
return;
            }
                usernameWsId.getOfflineUsers().add(toSend);

            notificationService.sendPrivateNotification(id);
            queueService.publishMessages(toSend,message );


        }


    public void deleteOfflineUsers(String sender) {
        usernameWsId.getOfflineUsers().remove(sender);
        logger.info("The sender is online {}", sender);
    }
}
