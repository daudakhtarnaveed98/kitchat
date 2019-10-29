package com.creativosoft.kitchat.message.services.message;

import com.creativosoft.kitchat.message.components.SessionManager;
import com.creativosoft.kitchat.message.components.Speech;
import com.creativosoft.kitchat.message.components.Text;
import com.creativosoft.kitchat.message.models.ChatMessage;
import com.creativosoft.kitchat.message.models.ChatMessageAttachment;
import com.creativosoft.kitchat.message.models.PendingMessage;
import com.creativosoft.kitchat.message.repositories.ChatMessageAttachmentRepository;
import com.creativosoft.kitchat.message.repositories.PendingMessageRepository;
import com.creativosoft.kitchat.message.services.attachment.MessageAttachmentGetterService;
import com.creativosoft.kitchat.message.services.voice.VoiceService;
import com.creativosoft.kitchat.message.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MessageHandlingService extends AbstractWebSocketHandler {
    private final Utils utils;
    private final MessageAttachmentGetterService attachmentGetterService;
    private final Speech speech;
    private final VoiceService voiceService;
    private final ChatMessageAttachmentRepository chatMessageAttachmentRepository;
    private final MessageSenderService messageSenderService;
    private final MessageSyncingService messageSyncingService;
    private final PendingMessageRepository pendingMessageRepository;

    @Autowired
    public MessageHandlingService(Utils utils, MessageAttachmentGetterService attachmentGetterService, Speech speech, VoiceService voiceService, ChatMessageAttachmentRepository chatMessageAttachmentRepository, MessageSenderService messageSenderService, MessageSyncingService messageSyncingService, PendingMessageRepository pendingMessageRepository) {
        this.utils = utils;
        this.attachmentGetterService = attachmentGetterService;
        this.speech = speech;
        this.voiceService = voiceService;
        this.chatMessageAttachmentRepository = chatMessageAttachmentRepository;
        this.messageSenderService = messageSenderService;
        this.messageSyncingService = messageSyncingService;
        this.pendingMessageRepository = pendingMessageRepository;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, @NotNull TextMessage payload) {
        // Variables.
        String messageJSON = payload.getPayload();
        ChatMessage messageObject = utils.jsonToChatMessage(messageJSON);

        String receiverId = messageObject.getReceiverId();

        boolean containsFile = messageObject.isFile();
        boolean containsVoice = messageObject.isVoice();

        CompletableFuture<Text> textCompletableFuture;

        if (containsFile || containsVoice) {
            Optional<ChatMessageAttachment> optionalChatMessageAttachment = attachmentGetterService.getAttachment(messageObject);

            if (optionalChatMessageAttachment.isPresent()) {

                if (containsVoice) {
                    String speechBase64 = optionalChatMessageAttachment.get().getChatMessageAttachedSpeech();
                    speech.setSpeech(speechBase64);
                    textCompletableFuture = voiceService.convertVoiceToText(speech);

                    try {
                        String recognizedText = textCompletableFuture.get().getText();
                        messageObject.setText(recognizedText);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                WebSocketSession receiverSession = SessionManager.getSocketSessionHashMap().get(receiverId);
                chatMessageAttachmentRepository.save(optionalChatMessageAttachment.get());
                messageSenderService.sendMessage(receiverSession, messageObject);
            }
        }
        else {
            if (messageObject.getCreatedAt().equals("null")) {
                messageSyncingService.syncMessages(session, messageObject);
            }
            else {
                WebSocketSession receiverSession = SessionManager.getSocketSessionHashMap().get(receiverId);
                messageSenderService.sendMessage(receiverSession, messageObject);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        String userURI = Objects.requireNonNull(session.getUri()).toString();
        String userName = userURI.substring(userURI.indexOf("?") + 1);

        SessionManager.getSocketSessionHashMap().put(userName, session);
        SessionManager.getSessionIdHashMap().put(session.getId(), userName);

        List<PendingMessage> pendingMessages = pendingMessageRepository.findByUserEmailAddress(userName);
        if (pendingMessages.size() > 0) {
            for (PendingMessage pendingMessage : pendingMessages) {
                ChatMessage messageObject = utils.jsonToChatMessage(pendingMessage.getPendingMessage());
                messageSenderService.sendMessage(session, messageObject);
                pendingMessageRepository.delete(pendingMessage);
            }
        }

        System.out.println(SessionManager.getSocketSessionHashMap());
        System.out.println(SessionManager.getSessionIdHashMap());
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, CloseStatus status) {
        String closedSessionId = session.getId();
        String closedSessionUsername = SessionManager.getSessionIdHashMap().get(closedSessionId);
        SessionManager.getSocketSessionHashMap().remove(closedSessionUsername);
        SessionManager.getSessionIdHashMap().remove(closedSessionId);

        System.out.println(SessionManager.getSocketSessionHashMap());
        System.out.println(SessionManager.getSessionIdHashMap());
    }
}
