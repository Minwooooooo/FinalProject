package com.example.demo.dto.responseDto.;

import com.example.demo.entity.Memo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemoResponseDto {
    private List<Memo> memoList;
}
