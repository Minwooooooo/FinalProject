package com.example.demo.service;


import com.example.demo.dto.responseDto.EnterMemberDto;
import com.example.demo.dto.responseDto.EnterMemberListDto;
import com.example.demo.dto.responseDto.MessageDto;
import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.RoomDetail;
import com.example.demo.repository.RoomDetailRepository;
import com.example.demo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatService {

    private final RoomRepository roomRepository;
    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatHandler chatHandler;
    private final RoomDetailRepository roomDetailRepository;

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage,String memberName,String image) {
        MessageDto messageDto=chatHandler.ChatTypeHandler(chatMessage,memberName,image);
        System.out.println(memberName+"ChatService");
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(),messageDto);
        System.out.println(chatMessage.getType());
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
            enterMembers(chatMessage.getRoomId());
        }
    }

    //접속중인 멤버 WebSocket으로 보내기
    @Transactional
    public void enterMembers(String roomId){

        ChatRoom chatRoom = roomRepository.findById(roomId)
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
