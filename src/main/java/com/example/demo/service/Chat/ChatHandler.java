package com.example.demo.service.Chat;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.httpDto.responseDto.TranslationResponseDto;
import com.example.demo.dto.messageDto.responseDto.EnterMemberDto;
import com.example.demo.dto.messageDto.responseDto.EnterMemberListDto;
import com.example.demo.dto.messageDto.responseDto.RoomDetailMessageDto;
import com.example.demo.dto.messageDto.MessageDto;
import com.example.demo.dto.messageDto.responseDto.ChatMessageResponseDto;
import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.room.RoomDetail;
import com.example.demo.repository.chat.ChatMessageRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.room.RoomDetailRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.translation.ChatTranslateService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private final ChatTranslateService chatTranslateService;
    private final ChatMessageRepository chatMessageRepository;


    // 토큰에서 프로필 사진 얻기
    public String getImageByToken(String token) {
        String id = jwtTokenProvider.tempClaim(token).getSubject();
        Member member = memberRepository.findById(id).get();
        String image =member.getMemberImage();
        return image;
    }


    // 채팅 핸들러
    public MessageDto ChatTypeHandler(ChatMessageResponseDto chatMessageResponseDto, String memberName, String memberId,String image) {
        // Dto 선언
        MessageDto temp_msg = null;

        // 채팅방 및 Detail 호출
        String roomId= chatMessageResponseDto.getRoomId();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("방을 찾을 수 없습니다."));
        RoomDetail roomDetail=roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow(()->new RuntimeException("방을 찾을 수 없습니다."));

        // 현재시간 호출
        Date date = new Date();
        String[] chatTimeDto=chatTime(date);

        // 채팅 접속 처리
        if (chatMessageResponseDto.getType().equals(ChatMessageResponseDto.MessageType.ENTER)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .type(chatMessageResponseDto.getType().ordinal())
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

            ChatMessage chatMessage= ChatMessage.builder()
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .type("ENTER")
                    .senderId(memberId)
                    .senderName(memberName)
                    .message(null)
                    .date(date)
                    .build();
            chatMessageRepository.save(chatMessage);
        }

        // 채팅 퇴장 처리
        else if (chatMessageResponseDto.getType().equals(ChatMessageResponseDto.MessageType.QUIT)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .type(chatMessageResponseDto.getType().ordinal())
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

            ChatMessage chatMessage= ChatMessage.builder()
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .type("QUIT")
                    .senderId(memberId)
                    .senderName(memberName)
                    .message(null)
                    .date(date)
                    .build();
            chatMessageRepository.save(chatMessage);
        }

        // 일반 채팅 처리 및 공란 무시
        else if (chatMessageResponseDto.getType().equals(ChatMessageResponseDto.MessageType.TALK)||!chatMessageResponseDto.getMessage().trim().equals("".trim())) { // websocket 연결요청
            String msg= chatMessageResponseDto.getMessage();
            if(chatBotService.botCheck(msg)){
                String new_message= chatBotService.botRunner(chatMessageResponseDto);
                temp_msg = MessageDto.builder()
                        .type(3)
                        .sender("알리미")
                        .image("")
                        .msg(new_message)
                        .build();
                return temp_msg;
            }
            temp_msg = MessageDto.builder()
                    .type(chatMessageResponseDto.getType().ordinal())
                    .sender(memberName)
                    .image(image)
                    .msg(chatMessageResponseDto.getMessage())
                    .chatDate(chatTimeDto[0])
                    .chatTime(chatTimeDto[1])
                    .build();

            ChatMessage chatMessage= ChatMessage.builder()
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .type("TALK")
                    .senderId(memberId)
                    .senderName(memberName)
                    .message(chatMessageResponseDto.getMessage())
                    .date(date)
                    .build();
            chatMessageRepository.save(chatMessage);
        }
        return temp_msg;
    }

    // 채팅방에 메시지 발송
    // To Do : ChatTypeHandler 병합
    public void sendChatMessage(ChatMessageResponseDto chatMessageResponseDto, String memberName,String memberId, String image) {
        MessageDto messageDto=ChatTypeHandler(chatMessageResponseDto,memberName,memberId,image);
        messageSendingOperations.convertAndSend("/sub/chat/room/"+ chatMessageResponseDto.getRoomId(),messageDto);
        if (chatMessageResponseDto.getType().equals(ChatMessageResponseDto.MessageType.ENTER)){
            enterMembers(chatMessageResponseDto.getRoomId());
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
            //System.out.println(responseElement);
            JsonObject responseObject=(JsonObject) ((JsonObject) responseElement).get("result");
            //System.out.println(responseObject);
            TranslationResponseDto translationResponseDto=TranslationResponseDto.builder()
                    .translatedText(responseObject.get("translatedText").toString())
                    .build();
            //System.out.println(responseObject.get("translatedText").toString());
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


        // 이미지 네이버 카카오시 오류
        for (int i = 0; i < roomDetail.getEnterMembers().size(); i++) {
            temp_enterMember= EnterMemberDto.builder()
                    .memberId(temp_enterMembers.get(i).getId().toString())
                    .memberName(temp_enterMembers.get(i).getMemberName())
                    .memberImg(temp_enterMembers.get(i).getMemberImage())
                    .build();
            resposeDto.add(temp_enterMember);
        }
        EnterMemberListDto enterMemberListDto = EnterMemberListDto.builder()
                .type(9)
                .enterMembers(resposeDto)
                .build();
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatRoom.getRoomId(),enterMemberListDto);
    }

    // 현재시간 출력
    private String[] chatTime(Date date){
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
