package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.RoomDetail;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RoomDetailRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class RoomHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RoomRepository roomRepository;
    private final RoomDetailRepository roomDetailRepository;
    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final SimpMessageSendingOperations messageSendingOperations;

    // 방 입장
    // 방 조회 -> (비밀번호확인) -> 권한 확인 -> 입장 처리
    @Transactional
    // asdf.com/auth/chat/enter/방id
    public ResponseDto<?> enterRoomHandler(String roomId, HttpServletRequest request) {
        // 접속 멤버 조회
        String token = jwtTokenProvider.getToken(request);
        Member member = memberRepository.findById(Long.valueOf(jwtTokenProvider.tempClaimNoBaerer(token).getSubject()))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

        // 방조회
        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        //비밀번호 확인
        if (chatRoom.isLock()) {
            System.out.println("비밀방");
        }

        // 권한 확인(인원수)
        if (chatRoom.getMaxEnterMember() < chatRoom.getMemberCount() + 1) {
            return ResponseDto.fail("Packed_Room", "입장 정원을 초과할 수 없습니다.");
        }

        // 권한 확인(Black)
        if (roomDetail.getBlackMembers().contains(member)) {
            return ResponseDto.fail("Bannde_Enter", "입장이 금지되었습니다.");
        }
        try {
            // 입장 처리-ChatRoom
            chatRoom.enterMember();
            chatRoom.setStatus(1);
            // 입장 처리-RoomDetail
            // 만약 이미 있는 멤버가 추가된다면????
            roomDetail.addMember(member);
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage(), "삐빅 오류");
        }


        return ResponseDto.success(chatRoom.getRoomName());
    }

    @Transactional
    public ResponseDto<?> quitRoomHandler(String roomId, HttpServletRequest request) {
        // 접속 멤버 조회
        String token = jwtTokenProvider.getToken(request);
        Member member = memberRepository.findById(Long.valueOf(jwtTokenProvider.tempClaimNoBaerer(token).getSubject()))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

        // 방조회
        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        // 퇴장 처리
        chatRoom.quitMember();
        roomDetail.removeMember(member);

        if (chatRoom.getMemberCount() <= 0) {
            chatRoom.setStatus(2); // 비활성화 상태로 변경
        }

        //방장이 퇴장시 입장 순으로 방장 세팅
        if (member == roomDetail.getRoomManager()) {

            for (int i = 0; i < roomDetail.getEnterMembers().size(); i++) {
                if (roomDetail.getEnterMembers().get(i) == null) {
                    // 모두가 나가거나 방장이 맨 마지막으로 나갔을 경우
                    if (i == roomDetail.getEnterMembers().size() - 1) {
                        roomDetail.setManager(null);
                    }

                    Member newManager = roomDetail.getEnterMembers().get(i + 1);
                    
                    if (newManager != null) {
                        roomDetail.setManager(newManager);

                        MessageDto messageDto = MessageDto.builder()
                                        .type(3)
                                        .sender("알림")
                                        .image("")
                                        .msg("방장이 " + newManager.getMemberName() + "님으로 교체되었습니다.")
                                        .build();
                        
                        // "방장이 newManager님으로 교체되었습니다." 공지 띄우기
                        messageSendingOperations.convertAndSend("/sub/chat/room/"+ roomId, messageDto);
                    }
                } else {
                    Member newManager = roomDetail.getEnterMembers().get(i);
                    roomDetail.setManager(newManager);

                    MessageDto messageDto = MessageDto.builder()
                            .type(3)
                            .sender("알림")
                            .image("")
                            .msg("방장이 " + newManager.getMemberName() + "님으로 교체되었습니다.")
                            .build();
                    
                    // "방장이 newManager님으로 교체되었습니다." 공지 띄우기
                    messageSendingOperations.convertAndSend("/sub/chat/room/"+ roomId, messageDto);
                }
            }

        }

        //자동 방 삭제
        //deleteRoomHandler(roomId);

        return ResponseDto.success("퇴장완료");
    }

    //방 삭제
    @Transactional
    public ResponseDto<?> deleteRoomHandler(String roomId) {

        // 방조회
        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        //방이 비활성화 상태(2)이고 인원수가 0인 경우
        if (chatRoom.getStatus() == 2 && chatRoom.getMemberCount() == 0) {
            roomRepository.delete(chatRoom);
            roomDetailRepository.delete(roomDetail);
        }

        return ResponseDto.success("방이 삭제되었습니다.");
    }


}
