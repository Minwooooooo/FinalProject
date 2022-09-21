package com.example.demo.Dto.ResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RoomListResponseDto {
    private String roomName;
    private String roomId;
    private String category;
    private int maxCount;
    private int nowCount;
}
