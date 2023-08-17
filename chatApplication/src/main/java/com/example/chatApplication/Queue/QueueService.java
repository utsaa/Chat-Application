package com.example.chatApplication.Queue;

import com.example.chatApplication.Queue.handlers.Subscriber;
import com.example.chatApplication.Queue.handlers.SubscriberWorker;
import com.example.chatApplication.model.ChatMessage;
import com.example.chatApplication.repositories.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QueueService {
    Logger logger= LoggerFactory.getLogger(QueueService.class);
    private final Map<String, Subscriber> subscriberMap;
    private final Map<String, SubscriberWorker> subscriberWorkerMap;


    private final UsernameWsId usernameWsId;
    private final SimpMessagingTemplate simpMessagingTemplate;
@Autowired
    public QueueService(final UsernameWsId usernameWsId, SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.subscriberMap = new HashMap<>();
        this.subscriberWorkerMap=new HashMap<>();
        this.usernameWsId=usernameWsId;
    }

    public void publishMessages(String toSend, ChatMessage message) {
        if (!subscriberMap.containsKey(toSend)){
            subscriberMap.put(toSend, new Subscriber(toSend, simpMessagingTemplate, usernameWsId));
        }
            Subscriber subscriberHandler= subscriberMap.get(toSend);
            subscriberHandler.publish(message);
            publish(toSend);

    }

    public void publish(String toSend){
        if (!subscriberMap.containsKey(toSend)){
            subscriberMap.put(toSend, new Subscriber(toSend, simpMessagingTemplate, usernameWsId));
        }
        logger.info("SubscriberWorkerMap {}", subscriberWorkerMap);
        if (!subscriberWorkerMap.containsKey(toSend)){
            Subscriber subscriberHandler= subscriberMap.get(toSend);
            logger.info("Subscriber map {}",subscriberMap);
            subscriberWorkerMap.put(toSend, new SubscriberWorker(subscriberHandler));
            new Thread(subscriberWorkerMap.get(toSend)).start();

        }
        subscriberWorkerMap.get(toSend).wakeUpIfNeeded();

    }

    public void publishAll(){
    for (Map.Entry<String, Subscriber> e: subscriberMap.entrySet()){
        publish(e.getKey());
    }
    }
}
