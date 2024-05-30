package proj.websocket_chat.domain.message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.websocket_chat.domain.member.Member;

@Entity
@Getter @Setter
public class ChatRoomMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
