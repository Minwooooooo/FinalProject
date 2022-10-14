package com.example.demo.service.Chat;

import com.example.demo.dto.messageDto.responseDto.RoomDetailMessageDto;
import com.example.demo.dto.messageDto.MessageDto;
import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.room.RoomDetail;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.room.RoomDetailRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatHandler {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatBotService chatBotService;
    private final RoomDetailRepository roomDetailRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessageSendingOperations messageSendingOperations;


    // 토큰에서 프로필 사진 얻기
    public String getImageByToken(String token) {
        String temp_id = jwtTokenProvider.tempClaim(token).getSubject();
        Long id = Long.valueOf(temp_id);
        Member member = memberRepository.findById(id).get();
        String image =member.getProfileImage();
        return image;
    }


    // 채팅 핸들러
    public MessageDto ChatTypeHandler(ChatMessage chatMessage, String memberName, String image) {
        // Dto 선언
        MessageDto temp_msg = null;

        // 채팅방 및 Detail 호출
        String roomId= chatMessage.getRoomId();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("방을 찾을 수 없습니다."));
        RoomDetail roomDetail=roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow(()->new RuntimeException("방을 찾을 수 없습니다."));

        // 현재시간 호출
        String[] chatTimeDto=chatTime();

        // 채팅 접속 처리
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .type(chatMessage.getType().ordinal())
                    .sender("")
                    .image("")
                    .msg(memberName+"님 하잉")
                    .build();

            //입장시 방정보 아이디 보내기
            RoomDetailMessageDto messageDto= RoomDetailMessageDto.builder()
                    .type(5)
                    .managerId(roomDetail.getRoomManager().getId())
                    .maxMember(chatRoom.getMaxEnterMember())
                    .connectedMember(chatRoom.getMemberCount())
                    .build();
            messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, messageDto);
        }

        // 채팅 퇴장 처리
        else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .type(chatMessage.getType().ordinal())
                    .sender("알림")
                    .image("")
                    .msg(memberName+"님 빠잉")
                    .build();

            RoomDetailMessageDto messageDto= RoomDetailMessageDto.builder()
                    .type(5)
                    .managerId(roomDetail.getRoomManager().getId())
                    .maxMember(chatRoom.getMaxEnterMember())
                    .connectedMember(chatRoom.getMemberCount())
                    .build();
            messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, messageDto);
            //인원수 -
        }

        // 일반 채팅 처리 및 공란 무시
        else if (chatMessage.getType().equals(ChatMessage.MessageType.TALK)||!chatMessage.getMessage().trim().equals("".trim())) { // websocket 연결요청
            String msg=chatMessage.getMessage();
            if(chatBotService.botCheck(msg)){
                String new_message= chatBotService.botRunner(chatMessage);
                temp_msg = MessageDto.builder()
                        .type(3)
                        .sender("알리미")
                        .image("")
                        .msg(new_message)
                        .build();
                return temp_msg;
            }
            temp_msg = MessageDto.builder()
                    .type(chatMessage.getType().ordinal())
                    .sender(memberName)
                    .image(image)
                    .msg(chatMessage.getMessage())
                    .chatDate(chatTimeDto[0])
                    .chatTime(chatTimeDto[1])
                    .build();
        }
        return temp_msg;
    }

    // 현재시간 출력
    private String[] chatTime(){
        Date date=new Date();
        int month=date.getMonth()+1;
        int day=date.getDate();
        int temp_hour=date.getHours();
        String hour = null;
        if (temp_hour<12){
            hour="오전 "+temp_hour;
        }
        if(temp_hour>=12){
            hour="오후 "+(temp_hour-12);
        }
        String chatTime;
        int minute=date.getMinutes();

        if(minute<10){
            chatTime=hour+":0"+minute;
        }
        else {
            chatTime=hour+":"+minute;
        }
        String chatDate=month+"월 "+day+"일";
        String[] chatTimeDto=new String[2];
        chatTimeDto[0]=chatDate;
        chatTimeDto[1]=chatTime;
        return chatTimeDto;
    }
}
