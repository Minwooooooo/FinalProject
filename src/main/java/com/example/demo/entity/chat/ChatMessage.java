package com.example.demo.entity.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessage {

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK, NOTICE, VAN,MANAGER,TEMP_2,TEMP_3,TEMP_4,LIST

    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String message; // 메시지
}
