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
import org.springframework.web.bind.annotation.PostMapping;

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
        String nickname = jwtTokenProvider.getMemberNameByToken(token);
        Long memberId = jwtTokenProvider.getMemberIdByToken(token);
        String image = chatHandler.getImageByToken(token);
        String msg = message.getMessage();
        System.out.println(nickname+" cont");
        if (message.getType().equals(ChatMessage.MessageType.ENTER)||message.getType().equals(ChatMessage.MessageType.QUIT)||message.getType().equals(ChatMessage.MessageType.NOTICE)||message.getType().equals(ChatMessage.MessageType.TALK)){
            if (!msg.trim().equals("".trim())){
                System.out.println("if");
            chatService.sendChatMessage(message,nickname,image  );
            }
        }else if(message.getType().equals(ChatMessage.MessageType.VAN)){
            Long vanId=Long.valueOf(message.getMessage());
            String roomId=message.getRoomId();
            roomHandler.vanHandler(memberId,vanId,roomId);

        }
        else if(message.getType().equals(ChatMessage.MessageType.MANAGER)){
            Long newManagerId=Long.valueOf(message.getMessage());
            String roomId=message.getRoomId();
            roomHandler.editManager(memberId,newManagerId,roomId);

        }

    }

    //번역
    @PostMapping("/chat/message/translation")
    public void translateMessage(String message) {
        chatService.sendTranslateMessage(message);
    }

}