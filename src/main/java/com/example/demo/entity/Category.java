package com.example.demo.entity;

import com.example.demo.dto.requestDto.CategoryRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    private String category;

    @Column
    private String story;


    public Category(CategoryRequestDto requestDto) {
        this.category = requestDto.getCategory();
        this.story = requestDto.getStory();
    }

    public void update(String story){
        this.story= story;
    }


//    for test
    public Category(String category, String story) {
        this.category = category;
        this.story = story;
    }
}
