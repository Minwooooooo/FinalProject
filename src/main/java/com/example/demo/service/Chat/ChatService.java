package com.example.demo.service.Chat;


import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.httpDto.responseDto.TranslationResponseDto;
import com.example.demo.dto.messageDto.responseDto.EnterMemberDto;
import com.example.demo.dto.messageDto.responseDto.EnterMemberListDto;
import com.example.demo.dto.messageDto.MessageDto;
import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.room.RoomDetail;
import com.example.demo.repository.room.RoomDetailRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import com.example.demo.service.translation.ChatTranslateService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatHandler chatHandler;
    private final RoomDetailRepository roomDetailRepository;
    private final ChatTranslateService chatTranslateService;



    // 채팅방에 메시지 발송
    public void sendChatMessage(ChatMessage chatMessage,String memberName,String image) {
        MessageDto messageDto=chatHandler.ChatTypeHandler(chatMessage,memberName,image);
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(),messageDto);
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
            enterMembers(chatMessage.getRoomId());
        }
    }


    // 번역 메세지
    public ResponseDto<?> sendTranslateMessage(String message) {
        String TranslateMessage = chatTranslateService.translate(message);
        JsonParser jsonParser = new JsonParser();
        Object obj = jsonParser.parse(TranslateMessage);
        JsonObject temp_response = (JsonObject) obj;
        try{
            JsonElement responseElement=temp_response.get("message");
            System.out.println(responseElement);
            JsonObject responseObject=(JsonObject) ((JsonObject) responseElement).get("result");
            System.out.println(responseObject);
            TranslationResponseDto translationResponseDto=TranslationResponseDto.builder()
                    .translatedText(responseObject.get("translatedText").toString())
                    .build();
            System.out.println(responseObject.get("translatedText").toString());
            return ResponseDto.success(translationResponseDto);
        }catch (Exception e){
            return ResponseDto.fail("Error",e.getMessage());
        }
    }


    //접속중인 멤버 WebSocket으로 보내기
    @Transactional
    public void enterMembers(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow(()->new RuntimeException("존재하지 않는 방입니다."));

        List<Member> temp_enterMembers=roomDetail.getEnterMembers();

        List<EnterMemberDto> resposeDto = new ArrayList<>();
        EnterMemberDto temp_enterMember;

        for (int i = 0; i < roomDetail.getEnterMembers().size(); i++) {
            temp_enterMember= EnterMemberDto.builder()
                    .memberId(temp_enterMembers.get(i).getId().toString())
                    .memberName(temp_enterMembers.get(i).getMemberName())
                    .memberImg(temp_enterMembers.get(i).getProfileImage())
                    .build();
            resposeDto.add(temp_enterMember);
        }
        EnterMemberListDto enterMemberListDto = EnterMemberListDto.builder()
                .type(9)
                .enterMembers(resposeDto)
                .build();
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatRoom.getRoomId(),enterMemberListDto);
    }

}
