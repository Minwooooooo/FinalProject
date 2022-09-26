package com.example.demo.service;

import com.example.demo.dto.responseDto.MessageDto;
import com.example.demo.entity.ChatMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.Principal;
import java.util.Optional;

@Service
public class ChatHandler {

    public MessageDto ChatTypeHandler(ChatMessage chatMessage, String memberName) {
        MessageDto temp_msg = null;
        if (chatMessage.getType().equals(chatMessage.getType().ENTER)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .sender("일림")
                    .msg(memberName+"님 하잉")
                    .build();
            //인원수 +
        }
        else if (chatMessage.getType().equals(chatMessage.getType().QUIT)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .sender("일림")
                    .msg(memberName+"님 빠잉")
                    .build();
            //인원수 -
        }
        else if (chatMessage.getType().equals(chatMessage.getType().TALK)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .sender(memberName)
                    .msg(chatMessage.getMessage())
                    .build();
        }
        return temp_msg;
    }
}
