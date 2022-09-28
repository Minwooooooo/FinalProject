package com.example.demo.dto.responseDto;

import com.example.demo.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoryResponseDto {
    List<Category> categories;
}
