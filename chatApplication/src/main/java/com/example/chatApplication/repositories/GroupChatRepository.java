package com.example.chatApplication.repositories;

import com.example.chatApplication.dto.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, String> {
}
