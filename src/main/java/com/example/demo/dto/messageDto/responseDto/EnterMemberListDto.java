package com.example.demo.dto.messageDto.responseDto;

import com.example.demo.dto.messageDto.responseDto.EnterMemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EnterMemberListDto {

    private int type;
    private List<EnterMemberDto> enterMembers;

}
