package com.example.chatApplication.services;

import com.example.chatApplication.dto.Message;
import com.example.chatApplication.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;
    @Override
    public List<Message> getMessage() {
        return messageRepository.findAll(Sort.by( Direction.ASC, "localDateTime"));
    }
}
