package proj.websocket_chat.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proj.websocket_chat.domain.member.Member;
import proj.websocket_chat.domain.message.ChatMessage;
import proj.websocket_chat.domain.message.ChatRoom;
import proj.websocket_chat.service.ChatMessageService;
import proj.websocket_chat.service.ChatRoomService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;


    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        if (member == null) {
            throw new RuntimeException("User not logged in");
        }

        Optional<ChatRoom> chatRoomOpt = chatRoomService.findById(message.getChatRoomId());
        if (!chatRoomOpt.isPresent()) {
            throw new RuntimeException("Chat room not found");
        }
        ChatRoom chatRoom = chatRoomOpt.get();

        ChatMessage entity = new ChatMessage();
        entity.setContent(message.getContent());
        entity.setMember(member);
        entity.setChatRoom(chatRoom);
        entity.setTimestamp(LocalDateTime.now());

        chatMessageService.saveMessage(entity);

        message.setSender(member.getUsername());
        message.setChatRoomId(chatRoom.getId());
        message.setTimestamp(entity.getTimestamp());
        return message;
    }

    @GetMapping("/search")
    public String searchMessages(@RequestParam String keyword, Model model) {
        List<ChatMessage> messages = chatMessageService.searchMessages(keyword)
                .stream()
                .map(entity -> {
                    ChatMessage message = new ChatMessage();
                    message.setContent(entity.getContent());
                    message.setSender(entity.getMember().getUsername());
                    message.setChatRoomId(entity.getChatRoom().getId());
                    return message;
                })
                .collect(Collectors.toList());

        model.addAttribute("messages", messages);
        return "search";
    }

    @GetMapping("/chatRooms")
    public String getAllChatRooms(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAllChatRooms();
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("newChatRoom", new ChatRoom());
        return "chatRooms";
    }

    @PostMapping("/chatRooms")
    public String createChatRoom(@ModelAttribute ChatRoom chatRoom, Model model) {
        chatRoomService.createChatRoom(chatRoom.getName());
        return "redirect:/chatRooms";
    }

    @GetMapping("/messages")
    public String getMessagesByChatRoom(@RequestParam Long chatRoomId, Model model, HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            throw new RuntimeException("User not logged in");
        }

        Optional<ChatRoom> chatRoomOpt = chatRoomService.findById(chatRoomId);
        if (!chatRoomOpt.isPresent()) {
            throw new RuntimeException("Chat room not found");
        }
        ChatRoom chatRoom = chatRoomOpt.get();
        List<ChatMessage> messages = chatMessageService.findMessagesByChatRoom(chatRoom);

        model.addAttribute("messages", messages);
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("currentUser", member.getUsername());
        return "message";
    }

//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/messages")
//    public ChatMessage sendMessage(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
//        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
//        if (member == null) {
//            throw new RuntimeException("User not logged in");
//        }
//
//        Optional<ChatRoom> chatRoomOpt = chatRoomService.findById(message.getChatRoomId());
//        if (!chatRoomOpt.isPresent()) {
//            throw new RuntimeException("Chat room not found");
//        }
//        ChatRoom chatRoom = chatRoomOpt.get();
//
//        ChatMessage entity = new ChatMessage();
//        entity.setContent(message.getContent());
//        entity.setMember(member);
//        entity.setChatRoom(chatRoom);
//
//        chatMessageService.saveMessage(entity);
//
//        message.setSender(member.getUsername());
//        message.setChatRoomId(chatRoom.getId());
//        return message;
//    }
//
//
//    @GetMapping("/search")
//    public String searchMessages(@RequestParam String keyword, Model model) {
//        List<ChatMessage> messages = chatMessageService.searchMessages(keyword)
//                .stream()
//                .map(entity -> {
//                    ChatMessage message = new ChatMessage();
//                    message.setContent(entity.getContent());
//                    message.setSender(entity.getMember().getUsername());
//                    message.setChatRoomId(entity.getChatRoom().getId());
//                    return message;
//                })
//                .collect(Collectors.toList());
//
//        model.addAttribute("messages", messages);
//        return "search";
//    }
//
//    @GetMapping("/chatRooms")
//    public String getAllChatRooms(Model model) {
//        List<ChatRoom> chatRooms = chatRoomService.findAllChatRooms();
//        model.addAttribute("chatRooms", chatRooms);
//        model.addAttribute("newChatRoom", new ChatRoom());
//        return "chatRooms";
//    }
//
//    @PostMapping("/chatRooms")
//    public String createChatRoom(@ModelAttribute ChatRoom chatRoom, Model model) {
//        chatRoomService.createChatRoom(chatRoom.getName());
//        return "redirect:/chatRooms";
//    }
//
//    @GetMapping("/messages")
//    public String getMessagesByChatRoom(@RequestParam Long chatRoomId, Model model) {
//        Optional<ChatRoom> chatRoomOpt = chatRoomService.findById(chatRoomId);
//        if (!chatRoomOpt.isPresent()) {
//            throw new RuntimeException("Chat room not found");
//        }
//        ChatRoom chatRoom = chatRoomOpt.get();
//        List<ChatMessage> messages = chatMessageService.findMessagesByChatRoom(chatRoom);
//        model.addAttribute("messages", messages);
//        model.addAttribute("chatRoomId", chatRoomId);
//        return "message";
//    }
}
