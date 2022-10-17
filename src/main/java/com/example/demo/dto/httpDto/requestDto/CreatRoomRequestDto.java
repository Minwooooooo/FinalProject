package com.example.demo.dto.httpDto.requestDto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Builder
public class CreatRoomRequestDto {
    private String roomName;
    private String category;
    private String roomImage;
    private long maxEnterMember;
    private boolean lock;
    private String roomPw;
}
