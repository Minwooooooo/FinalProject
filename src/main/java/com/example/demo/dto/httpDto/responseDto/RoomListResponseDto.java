package com.example.demo.dto.httpDto.responseDto;

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
    private boolean lock;
    private Long maxCount;
    private Long nowCount;
}
