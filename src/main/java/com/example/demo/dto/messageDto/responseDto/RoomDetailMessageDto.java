package com.example.demo.dto.messageDto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RoomDetailMessageDto {
    private int type;
    private String managerId;
    private Long maxMember;
    private Long connectedMember;
}
