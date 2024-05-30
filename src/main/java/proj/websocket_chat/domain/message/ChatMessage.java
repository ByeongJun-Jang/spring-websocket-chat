package proj.websocket_chat.domain.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.websocket_chat.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private LocalDateTime timestamp;

    public String getSender() {
        return member != null ? member.getUsername() : null;
    }

    public Long getChatRoomId() {
        return chatRoom != null ? chatRoom.getId() : null;
    }

    public void setChatRoomId(Long chatRoomId) {
        if (this.chatRoom == null) {
            this.chatRoom = new ChatRoom();
        }
        this.chatRoom.setId(chatRoomId);
    }

    public void setSender(String username) {
        if (this.member == null) {
            this.member = new Member();
        }
        this.member.setUsername(username);
    }

}
