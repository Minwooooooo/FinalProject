package com.example.demo.service;


import com.example.demo.dto.responseDto.MessageDto;
import com.example.demo.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomService chatRoomService;
    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatHandler chatHandler;



    // 채팅방에 메시지 발송
    public void sendChatMessage(ChatMessage chatMessage,String memberName,String image) {
        MessageDto messageDto=chatHandler.ChatTypeHandler(chatMessage,memberName,image);
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(),messageDto);
    }

}
