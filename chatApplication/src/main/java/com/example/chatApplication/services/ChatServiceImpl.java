package com.example.chatApplication.services;

import com.example.chatApplication.dto.GroupChat;
import com.example.chatApplication.dto.User;
import com.example.chatApplication.repositories.GroupChatRepository;
import com.example.chatApplication.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);
    @Autowired
    GroupChatRepository groupChatRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<GroupChat> getChats() {
        return groupChatRepository.findAll();
    }

    @Override
    public GroupChat createChat() {
        GroupChat groupChat= new GroupChat();
        groupChatRepository.save(groupChat);
        return groupChat;
    }

    @Override
    public void addChatUser(String groupId, String userId) {
        User user=userRepository.findById(userId).orElse(null);
        GroupChat groupChat=groupChatRepository.findById(groupId).orElse(null);
        if (user==null || groupChat==null) throw new RuntimeException("No such user present or chat present "
                +user+" "+ groupChat);
        List<User> users=new ArrayList<>(groupChat.getUsers());
        users.add(user);
        groupChat.setUsers(users);
        groupChatRepository.save(groupChat);
        logger.info("User {} is saved in group chat {}", user, groupChat);

    }
}
