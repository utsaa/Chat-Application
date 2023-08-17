package com.example.chatApplication.controller;

import com.example.chatApplication.dto.GroupChat;
import com.example.chatApplication.dto.Message;
import com.example.chatApplication.dto.User;
import com.example.chatApplication.model.ChatMessage;
import com.example.chatApplication.services.ChatService;
import com.example.chatApplication.services.MessageService;
import com.example.chatApplication.services.UserService;
import com.example.chatApplication.services.WsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class WsController {
    Logger logger = LoggerFactory.getLogger(WsController.class);
    @Autowired
    private WsService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;
    @PostMapping("/send-message")
    public ChatMessage sendMessage(@RequestBody final ChatMessage message){
        service.notifyFrontend(message);
        return message;

    }
    @PostMapping("/send-private-message/{groupId}")
    public ChatMessage sendPrivateMessage(@PathVariable final String groupId,@RequestBody final ChatMessage chatMessage ){

        service.notifyUser(groupId, chatMessage);
        logger.info( "sending message {} to id {}", chatMessage, groupId);
        return chatMessage;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
userService.addUser(user);
return user;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/groupChats")
    public List<GroupChat> getChats(){
        return chatService.getChats();
    }

    @GetMapping("/createChat")
    public GroupChat createChat(){
        return chatService.createChat();
    }

    @GetMapping("/addChatUser/{groupId}/{userId}")
    public void addChatUser(@PathVariable String groupId,@PathVariable String userId){
        logger.info(" Group Id {} and user id {}", groupId, userId);
        chatService.addChatUser(groupId, userId);
    }

    @GetMapping("/messages")
    public List<Message> getMessage(){
        return messageService.getMessage();
    }
}
