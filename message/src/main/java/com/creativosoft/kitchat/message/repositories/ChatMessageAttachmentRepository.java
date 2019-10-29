package com.creativosoft.kitchat.message.repositories;

import com.creativosoft.kitchat.message.models.ChatMessageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageAttachmentRepository extends JpaRepository <ChatMessageAttachment, String> {

}