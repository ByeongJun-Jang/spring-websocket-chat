package proj.websocket_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proj.websocket_chat.domain.member.Member;
import proj.websocket_chat.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member registerMember(String username, String loginId, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setLoginId(loginId);
        member.setPassword(password);
        return memberRepository.save(member);
    }

    public Member findMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
}
