package proj.websocket_chat.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.websocket_chat.domain.message.ChatMessage;
import proj.websocket_chat.domain.message.ChatRoomMember;

import java.util.Set;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String username;
    private String password;

    @OneToMany(mappedBy = "member")
    private Set<ChatMessage> messages;

    @OneToMany(mappedBy = "member")
    private Set<ChatRoomMember> chatRoomMembers;
}
