package com.example.demo.dto.httpDto.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoRequestDto {
    private String roomId;
    private String contents;
}
