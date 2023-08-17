package com.example.chatApplication.Queue.handlers;

import com.example.chatApplication.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberWorker implements Runnable{
    Logger logger= LoggerFactory.getLogger(Subscriber.class);
    private final Subscriber subscriber;
    private  final AtomicInteger tries;


    public Subscriber getSubscriber() {
        return subscriber;
    }



    public SubscriberWorker(final Subscriber subscriberHandler) {
        subscriber=subscriberHandler;
        tries=new AtomicInteger(0);

    }

    @Override
    public void run() {
logger.info("Inside run 1");
        synchronized (subscriber){
            while (true){
                logger.info("inside run");
                while (!subscriber.isMessageDeliverable() || tries.get()==2) {
                    try {
                        logger.info("size of subscriber messages {}",subscriber.getMessages().size());
                        subscriber.wait();
                        tries.set(0);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }
                subscriber.publishMessages();
                tries.getAndIncrement();

            }
        }

    }

    public void wakeUpIfNeeded() {
        synchronized (subscriber){
            subscriber.notifyAll();
        }
    }
}
