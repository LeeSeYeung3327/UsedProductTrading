package com.dcu.test.chat;

import com.dcu.test.member.Member;
import com.dcu.test.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ChatService chatMessageService;  // 메시지 저장 서비스

    @Autowired
    private UserColorManager userColorManager;  // 사용자별 랜덤 색상 관리

    // 메시지를 받으면 저장 후 브로드캐스트
    @MessageMapping("/chat.sendMessage/{roomId}")  // 📌 방 ID 포함
    @SendTo("/topic/public/{roomId}")  // 📌 특정 채팅방에만 전송
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
        message.setRoomId(roomId);
        message.setTimestamp(LocalDateTime.now());
        chatMessageService.saveMessage(message);
        return message;
    }

    // 채팅방 입장 시 호출
    @GetMapping("/chat/{roomId}")
    public String chatRoom(@PathVariable String roomId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // 사용자 정보 조회
        Member member = memberService.findMemberByEmail(email);
        String memberName = member.getName();
        String userColor = userColorManager.getUserColor(memberName);  // 랜덤 색상 부여

        model.addAttribute("roomId", roomId);
        model.addAttribute("memberName", memberName);
        model.addAttribute("userColor", userColor);  // 색상 추가

        return "chat";
    }

    // 저장된 채팅 메시지 반환 API (채팅방 입장 시 호출)
    @GetMapping("/chat/{roomId}/messages")
    @ResponseBody
    public List<ChatMessage> getMessagesByRoomId(@PathVariable String roomId) {
        return chatMessageService.getMessagesByRoomId(roomId);
    }
}
