package com.example.demo.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimeDto {
    private String roomName;
    private String category;
    private String time;
}
