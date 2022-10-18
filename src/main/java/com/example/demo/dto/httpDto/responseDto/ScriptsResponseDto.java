package com.example.demo.dto.httpDto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ScriptsResponseDto {
    private List<String> categories;
    private List<ScriptResponseDto> scriptResponseDto;
}
