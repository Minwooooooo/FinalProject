package com.example.demo.dto.responseDto;

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
    private long maxCount;
    private long nowCount;
}
