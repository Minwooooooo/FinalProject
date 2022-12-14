package com.example.demo.service.Room;


import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.room.RoomDetail;

import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.room.RoomDetailRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import com.example.demo.dto.httpDto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.httpDto.responseDto.RoomListResponseDto;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RoomDetailRepository roomDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 채팅방 생성
    @Transactional
    public ChatRoom createChatRoom(CreatRoomRequestDto creatRoomRequestDto, HttpServletRequest request) {
        // 멤버 확인
        String memberId=jwtTokenProvider.tempClaim(jwtTokenProvider.getToken(request)).getSubject();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        ChatRoom chatRoom = ChatRoom.create(creatRoomRequestDto);
        RoomDetail roomDetail = RoomDetail.create(chatRoom);
        chatRoomRepository.save(chatRoom);
        roomDetailRepository.save(roomDetail);
        roomDetail.setManager(member);
        return chatRoom;
    }

    // 방 목록
    public List<RoomListResponseDto> roomList() {
        List<RoomListResponseDto> responseDtos = new ArrayList<>();
        List<ChatRoom> temp_list = chatRoomRepository.findAllByStatusChecker(1);
        for (int i = 0; i < temp_list.size(); i++) {
            RoomListResponseDto temp_room= RoomListResponseDto.builder()
                    .roomId(temp_list.get(i).getRoomId())
                    .roomName(temp_list.get(i).getRoomName())
                    .roomImage(temp_list.get(i).getRoomImage())
                    .lock(temp_list.get(i).isLockChecker())
                    .category(temp_list.get(i).getCategory())
                    .maxCount(temp_list.get(i).getMaxEnterMember())
                    .nowCount(temp_list.get(i).getMemberCount())
                    .build();
            responseDtos.add(temp_room);
        }
        return responseDtos;
    }


}