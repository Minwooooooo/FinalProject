package com.example.demo.service;

import com.example.demo.dto.requestDto.MemoRequestDto;
import com.example.demo.dto.responseDto.MemoDto;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.Memo;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    private final RoomRepository roomRepository;

    private final MemberService memberService;

    @Transactional
    public MemoDto saveMemo(MemoRequestDto memoRequestDto, HttpServletRequest request) {

        ChatRoom room = roomRepository.findByRoomId(memoRequestDto.getRoomId());


        Member member = memberService.findMember(request);


        Memo memo = memoRepository.findByMemberAndRoomId(member, room.getRoomId());


        if (memo == null) {
            memo = Memo.builder()
                    .member(member)
                    .roomId(room.getRoomId())
                    .roomName(room.getRoomName())
                    .category(room.getCategory())
                    .contents(memoRequestDto.getContents())
                    .createDate(LocalDate.now())
                    .build();

            memoRepository.save(memo);

            return MemoDto.builder()
                    .memberName(member.getMemberName())
                    .roomId(memo.getRoomId())
                    .roomName(memo.getRoomName())
                    .category(memo.getCategory())
                    .contents(memo.getContents())
                    .createDate(memo.getCreateDate())
                    .build();

        } else {
            memo.updateMemo(memoRequestDto.getContents());


        return MemoDto.builder()
                .memberName(member.getMemberName())
                .roomName(memo.getRoomName())
                .category(memo.getCategory())
                .contents(memo.getContents())
                .createDate(memo.getCreateDate())
                .modifiedDate(memo.getModifiedDate())
                .build();
    }

    }


}
