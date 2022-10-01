package com.example.demo.controller;


import com.example.demo.entity.ChatMessage;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.ChatHandler;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatService chatService;
    private final ChatHandler chatHandler;


    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message,@Header("Authorization") String token) {
        String nickname = jwtTokenProvider.getMemberNameByToken(token);
        String image = chatHandler.getImageByToken(token);
        chatService.sendChatMessage(message,nickname,image);
    }
}
