package com.example.demo.service;


import com.example.demo.dto.responseDto.*;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RoomRepository roomRepository;
    private final RoomDetailRepository roomDetailRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ChatService chatService;


    private final SimpMessageSendingOperations messageSendingOperations;

    // 방 입장
    // 방 조회 -> (비밀번호확인) -> 권한 확인 -> 입장 처리
    @Transactional
    public ResponseDto<?> enterRoomHandler(String roomId, String roomPw, HttpServletRequest request){
        // 접속 멤버 조회
        String token= jwtTokenProvider.getToken(request);
        Member member=memberRepository.findById(Long.valueOf(jwtTokenProvider.tempClaimNoBaerer(token).getSubject()))
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        // 방조회
        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        //비밀번호 확인
        if(chatRoom.isLock()){
            if (!chatRoom.getRoomPw().equals(roomPw)){
                return ResponseDto.fail("Incorrect_Password","틀린 비밀번호입니다.");
            }
        }

        // 권한 확인(인원수)
        if(chatRoom.getMaxEnterMember()< chatRoom.getMemberCount()+1){
            return ResponseDto.fail("Packed_Room","입장 정원을 초과할 수 없습니다.");
        }

        // 권한 확인(Black)
        List<Member> blackMembers = null;
        try {
            blackMembers= roomDetail.getBlackMembers();
        }catch (NullPointerException e){
        }
        if(blackMembers!=null&&blackMembers.contains(member)){
            return ResponseDto.fail("Bannde_Enter","입장이 금지되었습니다.");
        }
        try {
            // 입장 처리-ChatRoom
            chatRoom.enterMember();
            // 입장 처리-RoomDetail
            // 만약 이미 있는 멤버가 추가된다면????
            roomDetail.addMember(member);

        }
        catch (Exception e){
            return ResponseDto.fail(e.getMessage(),"삐빅 오류");
        }
        EnterRoomResponseDto responseDto = EnterRoomResponseDto.builder()
                .roomId(chatRoom.getRoomId())
                .category(chatRoom.getCategory())
                .roomName(chatRoom.getRoomName())
                .build();

        return ResponseDto.success(responseDto);
    }

    //방 퇴장
    @Transactional
    public ResponseDto<?> quitRoomHandler(String roomId,HttpServletRequest request){
        // 접속 멤버 조회
        String token= jwtTokenProvider.getToken(request);
        Member member=memberRepository.findById(Long.valueOf(jwtTokenProvider.tempClaimNoBaerer(token).getSubject()))
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        // 방조회
        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
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
            
            // 인원수가 0인 경우
            if (roomDetail.getEnterMembers().get(0) == null) {
                
                roomDetail.setManager(null);

            } else {
                Member newManager = roomDetail.getEnterMembers().get(0);
                roomDetail.setManager(newManager);

                MessageDto messageDto = MessageDto.builder()
                        .type(3)
                        .sender("알림")
                        .image("")
                        .msg("방장이 " + newManager.getMemberName() + "님으로 교체되었습니다.")
                        .build();

                // "방장이 newManager님으로 교체되었습니다." 공지 띄우기
                messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, messageDto);
            }
        }

        //자동 방 삭제
        //deleteRoomHandler(roomId);
        chatService.enterMembers(roomId);
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


    // 강퇴
    @Transactional
    public ResponseDto<?> vanHandler(Long managerId,Long vanId,String roomId){

        //접속 멤버 조회
        Member managerMember=memberRepository.findById(managerId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        Member vanMember=memberRepository.findById(vanId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        //방 조회
        ChatRoom chatRoom = roomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        //권한 조회
        if(!checkManager(managerMember,roomDetail)){
            return ResponseDto.fail("","강퇴기능은 방장만 사용가능합니다.");
        }

        //해당방 블랙리스트에 추가
        roomDetail.vanMember(vanMember);
        //WebSocket으로 Van 대상 Id 전달
        VanMessageDto vanMessageDto= VanMessageDto.builder()
                .type(4)
                .vanId(vanId)
                .build();
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatRoom.getRoomId(),vanMessageDto);
        return ResponseDto.success("강퇴완료");
    }

    //권한확인
    public Boolean checkManager(Member member,RoomDetail roomDetail){
        Member manager=roomDetail.getRoomManager();
        return manager.equals(member);
    }


}
