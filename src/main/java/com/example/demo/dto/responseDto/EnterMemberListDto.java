package com.example.demo.dto.responseDto;

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
