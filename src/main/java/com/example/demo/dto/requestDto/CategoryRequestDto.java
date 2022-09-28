package com.example.demo.dto.requestDto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryRequestDto {
    private String category;
    private String story;


//    for test
    public CategoryRequestDto(String category, String story) {
        this.category = category;
        this.story = story;
    }
}
