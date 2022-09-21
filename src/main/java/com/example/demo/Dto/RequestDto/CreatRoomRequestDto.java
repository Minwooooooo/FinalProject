package com.example.demo.Dto.RequestDto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Builder
public class CreatRoomRequestDto {
    private String roomName;
    private String category;
    private int memberCount;
    private boolean lock;
    private String roomPw;
}
