package proj.websocket_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.websocket_chat.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);
}
