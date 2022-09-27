package com.example.demo.service;

import com.example.demo.dto.requestDto.MemoRequestDto;
import com.example.demo.dto.responseDto.MemoDto;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.Memo;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.RoomRepository;
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

    private final MemberRepository memberRepository;

    private final RoomRepository roomRepository;

    @Transactional
    public MemoDto saveMemo(MemoRequestDto memoRequestDto, HttpServletRequest request) {

        String memberName = request.getHeader("Member-Name");

        Member member = memberRepository.findByMemberName(memberName);

        //네임으로 식별 노노 토큰이랑 같이 식별 근데 어떻게 하는지 모르겠다...

        ChatRoom room = roomRepository.findByRoomId(memoRequestDto.getRoomId());


        Memo memo = Memo.builder()
                .member(member)
                .roomName(room.getRoomName())
                .category(room.getCategory())
                .contents(memoRequestDto.getContents())
                .createDate(LocalDate.now())
                .build();

        memoRepository.save(memo);

        return MemoDto.builder()
                .memberName(memberName)
                .roomName(memo.getRoomName())
                .category(memo.getCategory())
                .contents(memo.getContents())
                .createDate(memo.getCreateDate())
                .build();
    }


    @Transactional
    public List<MemoDto> getAllMemo(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id); //아이디로 찾는건가??
        Member member = optionalMember.get();

        List<MemoDto> memoDtos = new ArrayList<>();
        List<Memo> memoList = memoRepository.findAllByMember(member);

        for (Memo memo : memoList) {
            memoDtos.add(
                    MemoDto.builder()
                            .memberName(member.getMemberName())
                            .roomName(memo.getRoomName())
                            .category(memo.getCategory())
                            .contents(memo.getContents())
                            .createDate(memo.getCreateDate())
                            .modifiedDate(memo.getModifiedDate())
                            .build()
            );
        }
        return memoDtos;
    }


        @Transactional
        public MemoDto getMemo (Long id, String roomName){

            Optional<Member> optionalMember = memberRepository.findById(id); //아이디로 찾는건가??
            Member member = optionalMember.get();

            Memo memo = memoRepository.findByMemberAndRoomName(member, roomName);

            return MemoDto.builder()
                    .memberName(member.getMemberName())
                    .roomName(memo.getRoomName())
                    .category(memo.getCategory())
                    .contents(memo.getContents())
                    .createDate(memo.getCreateDate())
                    .modifiedDate(memo.getModifiedDate())
                    .build();
        }


        @Transactional
        public MemoDto updateMemo (Long id, MemoRequestDto memoRequestDto){
            ChatRoom room = roomRepository.findByRoomId(memoRequestDto.getRoomId());

            Optional<Member> optionalMember = memberRepository.findById(id); //아이디로 찾는건가??
            Member member = optionalMember.get();

            Memo memo = memoRepository.findByMemberAndRoomName(member, room.getRoomName());

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


    @Transactional
    public MemoDto deleteMemo (Long id, String roomName){
        Optional<Member> optionalMember = memberRepository.findById(id); //아이디로 찾는건가??
        Member member = optionalMember.get();

        Memo memo = memoRepository.findByMemberAndRoomName(member, roomName);
        memoRepository.delete(memo);
        return MemoDto.builder()
                .roomName(memo.getRoomName())
                .build();
    }


}
