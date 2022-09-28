package com.example.demo.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MypageDto {
    private String memberName;
    private String roomName;
    private String roomId;
    private String time;
    private String category;
}
