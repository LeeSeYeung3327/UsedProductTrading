package com.dcu.test.chat;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void saveMessage(ChatMessage message) {
        chatRepository.save(message);
    }

    public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return chatRepository.findAllByRoomIdOrderByTimestampAsc(roomId);
    }
}
