package proj.websocket_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.websocket_chat.domain.message.ChatMessage;
import proj.websocket_chat.domain.message.ChatRoom;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    List<ChatMessage> findByContentContaining(String keyword);
}
