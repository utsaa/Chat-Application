package com.example.chatApplication.services;

import com.example.chatApplication.dto.GroupChat;

import java.util.List;

public interface ChatService {
    List<GroupChat> getChats();

    GroupChat createChat();

    void addChatUser(String groupId, String userId);
}
