package proj.websocket_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.websocket_chat.domain.message.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
