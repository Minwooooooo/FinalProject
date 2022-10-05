package com.example.demo.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnterMemberDto {
    private String memberId;
    private String memberName;
    private String memberImg;
}
