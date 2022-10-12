package com.example.demo.dto.httpDto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private Long memberId;
    private String memberName;
    private String memberImg;
}
