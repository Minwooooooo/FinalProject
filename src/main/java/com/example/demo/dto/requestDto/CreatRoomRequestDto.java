package com.example.demo.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatRoomRequestDto {
    private String roomName;
    private String category;
    private long maxEnterMember;
    private boolean lock;
    private String roomPw;
}
