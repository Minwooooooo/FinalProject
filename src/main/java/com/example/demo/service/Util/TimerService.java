package com.example.demo.service.Util;

import com.example.demo.dto.httpDto.requestDto.TimerRequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.util.StudyTimer;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.room.RoomRepository;
import com.example.demo.repository.util.StudyTimerRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class TimerService {
    private final JwtTokenProvider jwtTokenProvider;
    private final StudyTimerRepository timerRepository;
    private final MemberRepository memberRepository;

    private final RoomRepository roomRepository;

    // 타이머 기록
    @Transactional
    public ResponseDto<?> StudyTimer(TimerRequestDto timerRequestDto, HttpServletRequest request){

        // 유저 확인
        String temp_id=jwtTokenProvider.tempClaimNoBaerer(jwtTokenProvider.getToken(request)).getSubject();
        Long member_id =Long.valueOf(temp_id);
        Member member=memberRepository.findById(member_id).get();

        // 방 확인
        ChatRoom chatRoom = roomRepository.findByRoomId(timerRequestDto.getRoomId());

        // 기존 타이머 여부 확인
        // 생성
        if(timerRepository.findByRoomIdAndMember(timerRequestDto.getRoomId(),member).isEmpty()){
            System.out.println("신규생성");
            StudyTimer studyTimer = StudyTimer.builder()
                    .member(member)
                    .roomId(timerRequestDto.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .category(chatRoom.getCategory())
                    .time(timerRequestDto.getStudyTime())
                    .build();
            timerRepository.save(studyTimer);
            return ResponseDto.success("타이머 저장 완료");
        }
        else {
            System.out.println("수정");
            // 수정
            StudyTimer studyTimer = timerRepository.findByRoomIdAndMember(timerRequestDto.getRoomId(), member).get();
            String beforeTime = studyTimer.getTime();
            studyTimer.editTime(timerRequestDto.getStudyTime());
            String msg = beforeTime + "에서 " + timerRequestDto.getStudyTime() + "으로 변경완료";

            return ResponseDto.success(msg);
        }
    }
}
