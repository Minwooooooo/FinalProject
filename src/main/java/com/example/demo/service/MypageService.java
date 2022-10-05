package com.example.demo.service;

import com.example.demo.dto.responseDto.MemoDto;
import com.example.demo.dto.responseDto.MypageDto;
import com.example.demo.dto.responseDto.TimeDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Memo;
import com.example.demo.entity.StudyTimer;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.StudyTimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MypageService {

    private final MemoRepository memoRepository;

    private final MemberService memberService;

    private final StudyTimerRepository studyTimerRepository;


    //사용자 정보 전부 가져오기
    @Transactional
    public MypageDto myInfo(HttpServletRequest request) {
        Member member = memberService.findMember(request);

        List<MemoDto> memoDtos = getAllMemo(request);

        List<TimeDto> TimeDtos = allMyTimer(request);

        return MypageDto.builder()
                .memberName(member.getMemberName())
                .memoDtoList(memoDtos)
                .timeDtoList(TimeDtos)
                .build();
    }


    //나의 모든 메모 불러오기
    @Transactional
    public List<MemoDto> getAllMemo(HttpServletRequest request) {

        Member member = memberService.findMember(request);

        List<MemoDto> memoDtos = new ArrayList<>();
        List<Memo> memoList = memoRepository.findAllByMember(member);

        for (Memo memo : memoList) {
            String temp_memo= memo.getContents();
//            if(memo.getContents().length()>30){
//                temp_memo=temp_memo.substring(0,30);
//            }
            memoDtos.add(
                    MemoDto.builder()
                            .roomId(memo.getRoomId())
                            .roomName(memo.getRoomName())
                            .category(memo.getCategory())
                            .contents(temp_memo)
                            .createDate(memo.getCreateDate())
                            .modifiedDate(memo.getModifiedDate())
                            .build()
            );
        }
        return memoDtos;
    }


    //특정 메모 불러오기
    @Transactional
    public MemoDto getMemo(HttpServletRequest request, String roomId) {

        Member member = memberService.findMember(request);

        Memo memo = memoRepository.findByMemberAndRoomId(member, roomId);

        return MemoDto.builder()
                .roomName(memo.getRoomName())
                .category(memo.getCategory())
                .contents(memo.getContents())
                .createDate(memo.getCreateDate())
                .modifiedDate(memo.getModifiedDate())
                .build();
    }


    //메모 삭제하기
    @Transactional
    public MemoDto deleteMemo(HttpServletRequest request, String roomId) {

        Member member = memberService.findMember(request);

        Memo memo = memoRepository.findByMemberAndRoomId(member, roomId);
        memoRepository.delete(memo);
        return MemoDto.builder()
                .roomName(memo.getRoomName())
                .contents(memo.getContents())
                .build();
    }


    //나의 모든 타이머 불러오기
    @Transactional
    public List<TimeDto> allMyTimer(HttpServletRequest request){
        Member member = memberService.findMember(request);

        List<TimeDto> TimeDtos = new ArrayList<>();
        List<StudyTimer> studyTimerList = studyTimerRepository.findAllByMember(member);

        for(StudyTimer studyTimer : studyTimerList){
            TimeDtos.add(
                    TimeDto.builder()
                            .roomName(studyTimer.getRoomName())
                            .category(studyTimer.getCategory())
                            .time(studyTimer.getTime())
                            .build()
            );
        }
        return TimeDtos;

    }

    //특정 타이머 불러오기
    @Transactional
    public TimeDto myTimer(HttpServletRequest request, String roomId){
        Member member = memberService.findMember(request);
        Optional<StudyTimer> optionalStudyTimer = studyTimerRepository.findByRoomIdAndMember(roomId, member);

        StudyTimer studyTimer = optionalStudyTimer.get();

        return TimeDto.builder()
                .roomName(studyTimer.getRoomName())
                .category(studyTimer.getCategory())
                .time(studyTimer.getTime())
                .build();

    }





}
