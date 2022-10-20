package com.example.demo.dto;

import com.example.demo.entity.util.Script;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoryResponseDto {
    List<Script> categories;
}
