package com.example.demo.service.Util;

import com.example.demo.dto.httpDto.requestDto.MemoRequestDto;
import com.example.demo.dto.httpDto.responseDto.MemoDto;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.util.Memo;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.util.MemoRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public MemoDto saveMemo(MemoRequestDto memoRequestDto, HttpServletRequest request) {

        ChatRoom room = chatRoomRepository.findByRoomId(memoRequestDto.getRoomId());

        Member member = memberService.findMember(request);

        Optional<Memo> temp_memo = memoRepository.findByMemberAndRoomId(member, room.getRoomId());
        Memo memo=null;
        if (temp_memo.isPresent()){
            memo=temp_memo.get();
        }

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

    //메모 불러오기
    public String getMemo(String roomId,HttpServletRequest request){

        String token= jwtTokenProvider.getToken(request);
        Member member=memberRepository.findById(Long.valueOf(jwtTokenProvider.tempClaim(token).getSubject()))
                .orElseThrow(()->new RuntimeException("존재하지 않는 ID입니다."));

        Optional<Memo> memo=memoRepository.findByMemberAndRoomId(member,roomId);
        if(memo.isEmpty()){
            return null;
        }
        else {
            return memo.get().getContents();
        }

    }


}
