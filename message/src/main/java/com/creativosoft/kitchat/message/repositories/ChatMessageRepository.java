package com.creativosoft.kitchat.message.repositories;

import com.creativosoft.kitchat.message.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    @Query("SELECT chatMessage FROM ChatMessage chatMessage WHERE LOWER(chatMessage.receiverId) = LOWER(:userEmailAddress)")
    List<ChatMessage> findByReceiverId(@Param("userEmailAddress") String userEmailAddress);

    @Query("SELECT chatMessage FROM ChatMessage chatMessage WHERE LOWER(chatMessage.senderId) = LOWER(:userEmailAddress)")
    List<ChatMessage> findBySenderId(@Param("userEmailAddress") String userEmailAddress);

    @Query("SELECT chatMessage FROM ChatMessage chatMessage WHERE LOWER(chatMessage.senderId) = LOWER(:userEmailAddress) OR LOWER(chatMessage.receiverId) = LOWER(:userEmailAddress)")
    List<ChatMessage> findMessagesToSync(@Param("userEmailAddress") String userEmailAddress);
}