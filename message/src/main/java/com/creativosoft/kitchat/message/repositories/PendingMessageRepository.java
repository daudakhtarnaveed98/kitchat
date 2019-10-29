package com.creativosoft.kitchat.message.repositories;

import com.creativosoft.kitchat.message.models.PendingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingMessageRepository extends JpaRepository<PendingMessage, String> {
    @Query("SELECT pendingMessage FROM PendingMessage pendingMessage WHERE LOWER(pendingMessage.receiverEmailAddress) = LOWER(:receiverEmailAddress) ORDER BY pendingMessage.messageId")
    List<PendingMessage> findByUserEmailAddress(@Param("receiverEmailAddress") String receiverEmailAddress);
}