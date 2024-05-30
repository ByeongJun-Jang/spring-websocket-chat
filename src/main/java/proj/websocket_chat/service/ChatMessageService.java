package proj.websocket_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proj.websocket_chat.domain.message.ChatMessage;
import proj.websocket_chat.domain.message.ChatRoom;
import proj.websocket_chat.repository.ChatMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> searchMessages(String keyword) {
        return chatMessageRepository.findByContentContaining(keyword);
    }

    public List<ChatMessage> findMessagesByChatRoom(ChatRoom chatRoom) {
        return chatMessageRepository.findByChatRoom(chatRoom);
    }
}
