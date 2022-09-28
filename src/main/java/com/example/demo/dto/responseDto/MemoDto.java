package com.example.demo.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class MemoDto {
    private String memberName;
    private String roomId;
    private String contents;
    private String roomName;
    private String category;
    private LocalDate createDate;
    private LocalDate modifiedDate;
}
