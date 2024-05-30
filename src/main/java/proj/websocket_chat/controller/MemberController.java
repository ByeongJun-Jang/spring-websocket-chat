package proj.websocket_chat.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import proj.websocket_chat.domain.member.Member;
import proj.websocket_chat.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member, Model model) {
        if (memberService.findMemberByLoginId(member.getLoginId()) != null) {
            model.addAttribute("error","이미 존재하는 아이디입니다.");
            return "register";
        }
        memberService.registerMember(member.getUsername(), member.getLoginId(), member.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute Member member, Model model){
        model.addAttribute("member", member);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Member member, HttpSession httpSession, Model model){
        Member loginMember = memberService.findMemberByLoginId(member.getLoginId());
        if (loginMember != null){
            httpSession.setAttribute("member", loginMember);
            return "redirect:/chatRooms";
        } else {
            model.addAttribute("error", "유효하지 않은 값입니다.");
            return "login";
        }
    }
}
