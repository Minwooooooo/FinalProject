package com.example.demo.controller.chat;


import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.Chat.ChatHandler;
import com.example.demo.service.Chat.ChatService;
import com.example.demo.service.Room.RoomHandler;
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
    private final RoomHandler roomHandler;


    // websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
    @MessageMapping("/chat/message")
    public void message(ChatMessage message,@Header("Authorization") String token) {
        // token에서 정보 조회
        String nickname = jwtTokenProvider.getMemberNameByToken(token);
        Long memberId = jwtTokenProvider.getMemberIdByToken(token);
        String image = chatHandler.getImageByToken(token);

        // msg 설정
        String msg = message.getMessage();

        // Type확인
        // 입,퇴장,일반 채팅
        if (message.getType().equals(ChatMessage.MessageType.ENTER)||message.getType().equals(ChatMessage.MessageType.QUIT)||message.getType().equals(ChatMessage.MessageType.NOTICE)||message.getType().equals(ChatMessage.MessageType.TALK)){
            // 공백 무시
            if (!msg.trim().equals("".trim())){
            chatService.sendChatMessage(message,nickname,image  );
            }
        // 유저 강퇴
        }else if(message.getType().equals(ChatMessage.MessageType.VAN)){
            Long vanId=Long.valueOf(message.getMessage());
            String roomId=message.getRoomId();
            roomHandler.vanHandler(memberId,vanId,roomId);
        // 방장변경
        }
        else if(message.getType().equals(ChatMessage.MessageType.MANAGER)){
            Long newManagerId=Long.valueOf(message.getMessage());
            String roomId=message.getRoomId();
            roomHandler.editManager(memberId,newManagerId,roomId);

        }

    }


}
