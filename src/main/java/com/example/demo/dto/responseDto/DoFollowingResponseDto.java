package com.example.demo.dto.responseDto;

import com.example.demo.entity.Follow;
import lombok.Builder;

import java.util.List;

@Builder
public class DoFollowingResponseDto {
    private List<Follow> followList;
    private String msg;
    private int size;
}
