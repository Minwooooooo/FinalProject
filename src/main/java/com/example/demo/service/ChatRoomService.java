package com.example.demo.service;


import com.example.demo.Entity.ChatRoom;
import com.example.demo.Entity.EnterMember;
import com.example.demo.Repository.EnterMemberRepository;
import com.example.demo.Repository.RoomRepository;
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
        ChatRoom chatRoom = ChatRoom.create(creatRoomRequestDto);
        roomRepository.save(chatRoom);
        return chatRoom;
    }

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String roomId) {
        EnterMember enterMember = new EnterMember(sessionId, roomId);
        enterMemberRepository.save(enterMember);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        EnterMember enterMember = enterMemberRepository.findBySessionId(sessionId);
        return enterMember.getRoomId();
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        EnterMember enterMember = enterMemberRepository.findBySessionId(sessionId);
        enterMemberRepository.delete(enterMember);
    }

    // 채팅방 유저수 조회
    public long getUserCount(String roomId) {
        ChatRoom chatRoom = roomRepository.findByRoomId(roomId);
        return chatRoom.getMemberCount();
    }

    // 채팅방에 입장한 유저수 +1
    public long plusUserCount(String roomId) {
        ChatRoom chatRoom = roomRepository.findByRoomId(roomId);
        Long memberCount  = chatRoom.getMemberCount() + 1;
        chatRoom.setMemberCount(memberCount);
        roomRepository.save(chatRoom);
        return memberCount;
    }

    // 채팅방에 입장한 유저수 -1
    public long minusUserCount(String roomId) {
        ChatRoom chatRoom = roomRepository.findByRoomId(roomId);
        Long memberCount  = chatRoom.getMemberCount() - 1;
        chatRoom.setMemberCount(memberCount);
        roomRepository.save(chatRoom);
        return memberCount;
    }


    // 방 목록
    public List<RoomListResponseDto> roomList() {
        List<RoomListResponseDto> responseDtos = new ArrayList<>();
        List<ChatRoom> temp_list = roomRepository.findAll();
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