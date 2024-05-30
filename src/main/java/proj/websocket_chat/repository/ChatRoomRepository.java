package proj.websocket_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.websocket_chat.domain.message.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
