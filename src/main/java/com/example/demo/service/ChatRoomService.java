package com.example.demo.service;


import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.EnterMember;
import com.example.demo.repository.EnterMemberRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.responseDto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final RoomRepository roomRepository;
    private final EnterMemberRepository enterMemberRepository;

    // 채팅방 생성
        public ChatRoom createChatRoom(CreatRoomRequestDto creatRoomRequestDto, HttpServletRequest request) {

        Optional<ChatRoom> chatRoomOptional= Optional.ofNullable(ChatRoom.create(creatRoomRequestDto));

        ChatRoom chatRoom= chatRoomOptional.orElseThrow(
                () -> new NullArgumentException("NOT_FOUND_CHAT_ROOM")
        );

//        todo id, name, category, maxEnterMember, memberCount, lock, roomPw의 null 확인 필요없으려나..?

        try {
            roomRepository.save(chatRoom);
        }catch (Exception e){
            System.err.println("CHATROOM_SAVE_ERR" + e);
        }


        return chatRoom;
    }

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String roomId) {
        Optional<EnterMember> enterMemberOptional= Optional.ofNullable(new EnterMember(sessionId, roomId));

        EnterMember enterMember = enterMemberOptional.orElseThrow(
                () -> new NullArgumentException("NOT_FOUND_ENTER_MEMBER")
        );

        try {
            enterMemberRepository.save(enterMember);
        }catch (Exception e){
            System.err.println("ENTER_MEMBER_SAVE_ERR");
        }
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        Optional<EnterMember> enterMemberOptional= Optional.ofNullable(
                enterMemberRepository.findBySessionId(sessionId)
        );

        EnterMember enterMember = enterMemberOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_SESSION_ID")
        );
        return enterMember.getRoomId();
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        Optional<EnterMember> enterMemberOptional= Optional.ofNullable(
                enterMemberRepository.findBySessionId(sessionId)
        );

        EnterMember enterMember = enterMemberOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_SESSION_ID")
        );

        try {
            enterMemberRepository.delete(enterMember);
        }catch (Exception e){
            System.err.println("DELETE_ERR" + e);
        }
    }

    // 채팅방 유저수 조회
    public long getUserCount(String roomId) {
        Optional<ChatRoom> chatRoomOptional= Optional.ofNullable(
                roomRepository.findByRoomId(roomId)
        );

        ChatRoom chatRoom = chatRoomOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_ROOM_ID")
        );

        return chatRoom.getMemberCount();
    }

    // 채팅방에 입장한 유저수 +1
    public long plusUserCount(String roomId) {
        Optional<ChatRoom> chatRoomOptional= Optional.ofNullable(
                roomRepository.findByRoomId(roomId)
        );

        ChatRoom chatRoom = chatRoomOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_ROOM_ID")
        );

        Long memberCount  = chatRoom.getMemberCount() + 1;

        chatRoom.setMemberCount(memberCount);

        try {
            roomRepository.save(chatRoom);
        }catch (Exception e){
            System.err.println("CHAT_ROOM_USER_NUMBER_PLUS_ERR" + e);
        }

        return memberCount;
    }

    // 채팅방에 입장한 유저수 -1
    public long minusUserCount(String roomId) {
        Optional<ChatRoom> chatRoomOptional= Optional.ofNullable(
                roomRepository.findByRoomId(roomId)
        );
        ChatRoom chatRoom = chatRoomOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_ROOM_ID")
        );

        Long memberCount  = chatRoom.getMemberCount() - 1;

        chatRoom.setMemberCount(memberCount);

        try {
            roomRepository.save(chatRoom);
        }catch (Exception e){
            System.err.println("CHAT_ROOM_USER_NUMBER_MINUS_ERR" + e);
        }

        return memberCount;
    }


    // 방 목록
    public List<RoomListResponseDto> roomList() {
        List<RoomListResponseDto> responseDtos = new ArrayList<>();

        Optional<List<ChatRoom>> optionalChatRooms= Optional.ofNullable(roomRepository.findAll());
        List<ChatRoom> temp_list = optionalChatRooms.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_CHAT_ROOM_LIST")
        );

        for (int i = 0; i < temp_list.size(); i++) {
            RoomListResponseDto temp_room= RoomListResponseDto.builder()
                    .roomId(temp_list.get(i).getRoomId())
                    .roomName(temp_list.get(i).getRoomName())
                    .category(temp_list.get(i).getCategory().toString())
                    .maxCount(temp_list.get(i).getMaxEnterMember())
                    .nowCount(temp_list.get(i).getMemberCount())
                    .build();
            responseDtos.add(temp_room);
        }
        return responseDtos;
    }
}
