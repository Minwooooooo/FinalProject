package com.example.demo.Service;

import com.example.demo.Dto.RequestDto.CreatRoomRequestDto;
import com.example.demo.Dto.ResponseDto.ResponseDto;
import com.example.demo.Dto.ResponseDto.RoomListResponseDto;
import com.example.demo.Entity.Room;
import com.example.demo.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final RoomRepository roomRepository;

    public String creatRoom(CreatRoomRequestDto creatRoomRequestDto, HttpServletRequest request) {
        Room newRoom = Room.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(creatRoomRequestDto.getRoomName())
                .category(creatRoomRequestDto.getCategory())
                .memberCount(creatRoomRequestDto.getMemberCount())
                .lock(creatRoomRequestDto.isLock())
                .roomPw(creatRoomRequestDto.getRoomPw())
                .build();
        roomRepository.save(newRoom);
        return "방 생성완료";
        // 추후 HttpServletResponse 추가후 헤더에 방 ID 주기
    }

    public List<RoomListResponseDto> roomList() {
        List<RoomListResponseDto> responseDtos = new ArrayList<>();
        List<Room> temp_list = roomRepository.findAll();
        for (int i = 0; i < temp_list.size(); i++) {
            RoomListResponseDto temp_room= RoomListResponseDto.builder()
                    .roomId(temp_list.get(i).getRoomId())
                    .roomName(temp_list.get(i).getRoomName())
                    .category(temp_list.get(i).getCategory().toString())
                    .maxCount(temp_list.get(i).getMemberCount())
                    .nowCount(0)
                    .build();
            responseDtos.add(temp_room);
        }
        return responseDtos;
    }



}
