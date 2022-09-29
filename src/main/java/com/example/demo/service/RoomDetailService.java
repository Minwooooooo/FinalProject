package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.entity.RoomDetail;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RoomDetailRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomDetailService {

    private final RoomDetailRepository roomDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;


    //방 입장
    @Transactional
    public void enterRoom(String roomId, Member member){
        //토큰 or HSrequest
        RoomDetail roomDetail=roomDetailRepository.findByRoomId(roomId).get();
        roomDetail.addMember(member);
    }

    //방 퇴장
    @Transactional
    public void quitRoom(String roomId, Member member){
        //토큰 or HSrequest
        RoomDetail roomDetail=roomDetailRepository.findByRoomId(roomId).get();
        roomDetail.removeMember(member);
    }
    //권한부여
    @Transactional
    public void setManager(String roomId, Member member){
        RoomDetail roomDetail=roomDetailRepository.findByRoomId(roomId).get();
        roomDetail.setManager(member);
    }

    //강퇴
    //방 삭제
}
