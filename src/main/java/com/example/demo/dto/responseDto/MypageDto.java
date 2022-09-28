package com.example.demo.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MypageDto {
    private String memberName;
    private List<MemoDto> memoDtoList;
    private List<TimeDto> timeDtoList;
}
