package com.example.demo.dto.messageDto.responseDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessageResponseDto {

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, // 0: 입장
        QUIT, // 1 : 퇴장
        TALK, // 2 : 채팅
        NOTICE, // 3 : 공지
        VAN,  // 4 : 강퇴
        ROOM_INFO, // 5 : 방 정보
        MANAGER, // 6 : 방장 변경
        REPORT, // 7 : 신고하기
        PINGPONG, // 8 : 접속체크
        LIST // 9 : 접속중인 인원 리스트

    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String message; // 메시지
    private String etc;
}
