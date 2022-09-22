package com.example.demo.service;

import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.responseDto.RoomListResponseDto;
import com.example.demo.model.ChatRoom;
import com.example.demo.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final RoomRepository roomRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(CreatRoomRequestDto creatRoomRequestDto) {
        ChatRoom chatRoom = ChatRoom.create(creatRoomRequestDto);
        roomRepository.save(chatRoom);
        return chatRoom;
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
