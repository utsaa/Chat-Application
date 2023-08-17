package com.example.chatApplication.services;

import com.example.chatApplication.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    void addUser(User user);

    List<User> getUsers();

    void addUserWsInMemory(String sender, String name);

    boolean checkUserPresent(String sender);
}
