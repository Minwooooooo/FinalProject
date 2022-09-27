package com.example.demo.dto.requestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Builder
public class MemoRequestDto {
    private Long id;
    private String roomName;
    private String content;
    private String category;
}
