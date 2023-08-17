package com.example.chatApplication.services;


import com.example.chatApplication.repositories.UserRepository;
import com.example.chatApplication.dto.User;
import com.example.chatApplication.repositories.UsernameWsId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsernameWsId usernameWsId;
    @Override
    public void addUser(User user) {

        List<User> users=userRepository.findUserByName( user.getName());
        if (users.size()==0){
            userRepository.save(user);
            logger.info("Saved user {}", user);
        }else {
            logger.info("User is already in db");
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUserWsInMemory(String sender, String name) {
        usernameWsId.getRepo().put(sender, name);
    }

    @Override
    public boolean checkUserPresent(String sender) {
        List<User> users=userRepository.findUserByName(sender);
        return (users.size()!=0 && users!= null);
    }
}
