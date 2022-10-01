package com.example.demo.service;

import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.RoomDetail;
import com.example.demo.repository.RoomDetailRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomDetailService {

    private final RoomDetailRepository roomDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;


    //방 입장
    @Transactional
    public void enterRoom(ChatRoom chatRoom, Member member){
        //토큰 or HSrequest
        RoomDetail roomDetail=roomDetailRepository.findByChatRoom(chatRoom).get();
        roomDetail.addMember(member);
    }

    //방 퇴장
    @Transactional
    public void quitRoom(ChatRoom chatRoom, Member member){
        //토큰 or HSrequest
        RoomDetail roomDetail=roomDetailRepository.findByChatRoom(chatRoom).get();
        roomDetail.removeMember(member);
    }
    //권한부여
    @Transactional
    public void setManager(ChatRoom chatroom, Member member){
        RoomDetail roomDetail=roomDetailRepository.findByChatRoom(chatroom).get();
        roomDetail.setManager(member);
    }

    //강퇴


    //방 삭제
}
