package com.example.demo.service;


import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.RoomDetail;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RoomDetailRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.responseDto.RoomListResponseDto;
import com.example.demo.security.jwt.JwtTokenProvider;
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
    private final RoomDetailRepository roomDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(CreatRoomRequestDto creatRoomRequestDto, HttpServletRequest request) {
        // 멤버 확인
        Long memberId=Long.valueOf(jwtTokenProvider.tempClaimNoBaerer(jwtTokenProvider.getToken(request)).getSubject());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        ChatRoom chatRoom = ChatRoom.create(creatRoomRequestDto);
        RoomDetail roomDetail = RoomDetail.create(chatRoom);
        roomRepository.save(chatRoom);
        roomDetailRepository.save(roomDetail);
        roomDetail.setManager(member);
        return chatRoom;
    }

    // 방 목록
    public List<RoomListResponseDto> roomList() {
        List<RoomListResponseDto> responseDtos = new ArrayList<>();
        List<ChatRoom> temp_list = roomRepository.findAllByStatusOrStatus(0, 1);
        for (int i = 0; i < temp_list.size(); i++) {
            RoomListResponseDto temp_room= RoomListResponseDto.builder()
                    .roomId(temp_list.get(i).getRoomId())
                    .roomName(temp_list.get(i).getRoomName())
                    .lock(temp_list.get(i).isLock())
                    .category(temp_list.get(i).getCategory().toString())
                    .maxCount(temp_list.get(i).getMaxEnterMember())
                    .nowCount(temp_list.get(i).getMemberCount())
                    .build();
            responseDtos.add(temp_room);
        }
        return responseDtos;
    }


}