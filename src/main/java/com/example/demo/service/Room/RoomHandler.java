package com.example.demo.service.Room;


import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.httpDto.responseDto.EnterRoomResponseDto;
import com.example.demo.dto.messageDto.MessageDto;
import com.example.demo.dto.messageDto.responseDto.RoomDetailMessageDto;
import com.example.demo.dto.messageDto.responseDto.VanMessageDto;
import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.room.RoomDetail;
import com.example.demo.entity.room.RoomNotice;
import com.example.demo.repository.chat.ChatMessageRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.room.RoomDetailRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import com.example.demo.repository.room.RoomNoticeRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.Chat.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomDetailRepository roomDetailRepository;
    private final MemberRepository memberRepository;
    private final RoomNoticeRepository roomNoticeRepository;
    private final ChatHandler chatHandler;
    private final ChatMessageRepository chatMessageRepository;


    private final SimpMessageSendingOperations messageSendingOperations;

    // 방 입장
    // 방 조회 -> (비밀번호확인) -> 권한 확인 -> 입장 처리
    @Transactional
    public ResponseDto<?> enterRoomHandler(String roomId, String roomPw, HttpServletRequest request){
        // 접속 멤버 조회
        String token= jwtTokenProvider.getToken(request);
        Member member=memberRepository.findById(jwtTokenProvider.tempClaim(token).getSubject())
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        // 방조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();
        if(chatRoom.getStatusChecker()==2){
            return ResponseDto.fail("Deactivated_Room","비활성화된 방입니다.");
        }

        //비밀번호 확인
        if(chatRoom.isLockChecker()){
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

            // 입장 처리-RoomDetail
            roomDetail.addMember(member);
            Long memberCount= (long) roomDetail.getEnterMembers().size();
            // 입장 처리-ChatRoom
            chatRoom.editMember(memberCount);

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
        Member member=memberRepository.findById(jwtTokenProvider.tempClaim(token).getSubject())
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        // 방조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        // 퇴장 처리
        roomDetail.removeMember(member);
        member.setlastPing(null);
        Long memberCount= (long) roomDetail.getEnterMembers().size();
        // 입장 처리-ChatRoom
        chatRoom.editMember(memberCount);

        if (chatRoom.getMemberCount() <= 0) {
            chatRoom.setStatusChecker(2); // 비활성화 상태로 변경
        }

        //방장이 퇴장시 입장 순으로 방장 세팅
        autoChangeRoomManager(roomId, member, chatRoom, roomDetail);
        //자동 방 삭제
        if(!deleteRoomHandler(roomId)){
            chatHandler.enterMembers(roomId);
        }
        return ResponseDto.success("퇴장완료");
    }


    //방 삭제
    @Transactional
    public Boolean deleteRoomHandler(String roomId) {

        // 방조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();
        Optional<RoomNotice> roomNotice = roomNoticeRepository.findByChatRoom(chatRoom);

        //방이 비활성화 상태(2)이고 인원수가 0인 경우
        if (chatRoom.getStatusChecker() == 2 && chatRoom.getMemberCount() == 0) {

            if(roomDetail.getBlackMembers()!=null){
                while (roomDetail.getBlackMembers().size()!=0) {
                    roomDetail.getBlackMembers().remove(0);
                }
            }
            roomNotice.ifPresent(roomNoticeRepository::delete);

            roomDetailRepository.delete(roomDetail);
            chatRoomRepository.delete(chatRoom);
            return true;
        }
        return false;
    }


    // 강퇴
    @Transactional
    public ResponseDto<?> vanHandler(String managerId,String vanId,String roomId){
        Date date=new Date();

        //접속 멤버 조회
        Member managerMember=memberRepository.findById(managerId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        Member vanMember=memberRepository.findById(vanId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        //방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
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

        MessageDto messageDto = MessageDto.builder()
                .type(3)
                .sender("알림")
                .image("")
                .msg(vanMember.getMemberName()+"님이 강퇴되었습니다.")
                .build();
        messageSendingOperations.convertAndSend("/sub/chat/room/"+chatRoom.getRoomId(),messageDto);
        vanMember.setlastPing(null);

        ChatMessage chatMessage= ChatMessage.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .type("VAN")
                .senderId(managerMember.getId())
                .senderName(managerMember.getMemberName())
                .message(vanMember.getMemberName()+"("+vanMember.getId()+")"+"님이 추방당하셨습니다.")
                .date(date)
                .build();
        chatMessageRepository.save(chatMessage);

        return ResponseDto.success("강퇴완료");
    }

    //권한확인
    public Boolean checkManager(Member member,RoomDetail roomDetail){
        Member manager=roomDetail.getRoomManager();
        return manager.equals(member);
    }

    //퇴장처리
    public void quitProcess(HttpServletRequest request){
        // 접속 멤버 조회
        String token= jwtTokenProvider.getToken(request);
        Member member=memberRepository.findById(jwtTokenProvider.tempClaim(token).getSubject())
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        System.out.println("QUIT_PROCESS : "+member.getMemberName());

        Optional<RoomDetail> roomDetail=roomDetailRepository.findByEnterMembers(member);
        if(roomDetail.isPresent()){
            roomDetail.get().getEnterMembers().remove(member);
        }
    }


    //방장위임
    public void editManager(String managerId, String newManagerId, String roomId) {
        Date date=new Date();
        //접속 멤버 조회
        Member managerMember=memberRepository.findById(managerId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        Member newManagerMember=memberRepository.findById(newManagerId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));


        //방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();

        //권한 조회
        if(checkManager(managerMember,roomDetail)){
            roomDetail.setManager(newManagerMember);

            MessageDto messageDto = MessageDto.builder()
                    .type(3)
                    .sender("알림")
                    .image("")
                    .msg("방장이 " + newManagerMember.getMemberName() + "님으로 교체되었습니다.")
                    .build();

            // "방장이 newManager님으로 교체되었습니다." 공지 띄우기
            messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, messageDto);

            RoomDetailMessageDto roomDetailMessageDto= RoomDetailMessageDto.builder()
                    .type(5)
                    .managerId(newManagerId)
                    .maxMember(chatRoom.getMaxEnterMember())
                    .connectedMember(chatRoom.getMemberCount())
                    .build();
            messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, roomDetailMessageDto);

            ChatMessage chatMessage= ChatMessage.builder()
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .type("MANAGER")
                    .senderId(managerMember.getId())
                    .senderName(managerMember.getMemberName())
                    .message("방장이 "+newManagerMember.getMemberName()+"("+newManagerMember.getMemberName()+")님으로 교체되었습니다.")
                    .date(date)
                    .build();
            chatMessageRepository.save(chatMessage);
        }
    }
    @Transactional
    public void pingPongCheck(String memberId,String roomId){
        //멤버 조회
        Member member=memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        Date date =new Date();
        member.setlastPing(date);

        //방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));
        RoomDetail roomDetail = roomDetailRepository.findByChatRoom(chatRoom)
                .orElseThrow();
        List<Member> enterMembers= roomDetail.getEnterMembers();

        // 1. List X Ping O -> 강제퇴장
        if(!enterMembers.contains(member)){
            autoChangeRoomManager(roomId, member, chatRoom, roomDetail);
            VanMessageDto vanMessageDto= VanMessageDto.builder()
                    .type(8)
                    .vanId(memberId)
                    .build();
            messageSendingOperations.convertAndSend("/sub/chat/room/"+chatRoom.getRoomId(),vanMessageDto);


            member.setlastPing(null);
        }

        // 2. List O Ping X -> 퇴장처리
        List<ChatRoom> allChatrooms=chatRoomRepository.findAllByStatusChecker(1);
        if(allChatrooms.size()!=0){
            for (int i = 0; i < allChatrooms.size(); i++) {
                ChatRoom temp_ChatRoom=allChatrooms.get(i);
                RoomDetail temp_RoomDetail=roomDetailRepository.findByChatRoom(temp_ChatRoom)
                        .orElseThrow();
                List<Member> temp_EnterMembers=temp_RoomDetail.getEnterMembers();
                List<Member> quitMember=new ArrayList<>();
                for (Member temp_enterMember : temp_EnterMembers) {
                    System.out.println(temp_enterMember + " 체크 시작");
                    Date temp_lastPing = temp_enterMember.getLastPing();
                    if (temp_lastPing != null && date.getTime() - temp_lastPing.getTime() >= 5000) {
                        System.out.println("5000이상 차이");
                        quitMember.add(temp_enterMember);
                    }
                }
                temp_EnterMembers.removeAll(quitMember);
                for (int j = 0; j < quitMember.size(); j++) {
                    MessageDto temp_msg = MessageDto.builder()
                            .type(1)
                            .sender("알림")
                            .image("")
                            .msg(quitMember.get(i).getMemberName()+"님 빠잉")
                            .build();
                    messageSendingOperations.convertAndSend("/sub/chat/room/"+chatRoom.getRoomId(),temp_msg);
                    quitMember.get(i).setlastPing(null);
                    autoChangeRoomManager(roomId, quitMember.get(i), chatRoom, roomDetail);
                }
                chatHandler.enterMembers(chatRoom.getRoomId());
            }
        }
    }

    public void autoChangeRoomManager(String roomId, Member member, ChatRoom chatRoom, RoomDetail roomDetail) {
        if (member == roomDetail.getRoomManager()) {
            // 인원수가 0인 경우
            if (roomDetail.getEnterMembers().isEmpty()) {
                roomDetail.setManager(null);

            } else {
                Member newManager = roomDetail.getEnterMembers().get(0);
                roomDetail.setManager(newManager);

                // "방장이 newManager님으로 교체되었습니다." 공지 띄우기
                MessageDto messageDto = MessageDto.builder()
                        .type(3)
                        .sender("알림")
                        .image("")
                        .msg("방장이 " + newManager.getMemberName() + "님으로 교체되었습니다.")
                        .build();
                messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, messageDto);

                //바뀐방장 정보보내기
                RoomDetailMessageDto roomDetailMessageDto= RoomDetailMessageDto.builder()
                        .type(5)
                        .managerId(roomDetail.getRoomManager().getId())
                        .maxMember(chatRoom.getMaxEnterMember())
                        .connectedMember(chatRoom.getMemberCount())
                        .build();
                messageSendingOperations.convertAndSend("/sub/chat/room/" + roomId, roomDetailMessageDto);
            }
        }
    }


    public void reportHandler(String managerId, String roomId, String reportId, String reportReason) {
        Date date =new Date();
        //멤버 조회
        Member managerMember=memberRepository.findById(managerId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));
        Member reportMember=memberRepository.findById(reportId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        //방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("존재하지않는 방입니다."));

        ChatMessage chatMessage= ChatMessage.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .type("REPORT")
                .senderId(managerMember.getId())
                .senderName(managerMember.getMemberName())
                .message(reportMember.getMemberName()+"("+reportMember.getNaverId()+")")
                .etc(reportReason)
                .date(date)
                .build();
        chatMessageRepository.save(chatMessage);
    }
}
