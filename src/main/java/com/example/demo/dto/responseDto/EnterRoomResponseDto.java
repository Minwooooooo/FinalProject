package com.example.demo.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnterRoomResponseDto {
    private String roomId;
    private String category;
    private String roomName;
}
