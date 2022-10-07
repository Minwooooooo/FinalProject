package com.example.demo.entity.util;

import com.example.demo.dto.httpDto.requestDto.CategoryRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private int type;

    @Column
    private String category;

    @Column
    private String story;


    public Script(CategoryRequestDto requestDto) {
        this.category = requestDto.getCategory();
        this.story = requestDto.getStory();
    }

    public void update(CategoryRequestDto requestDto){
        this.category = requestDto.getCategory();
        this.story = requestDto.getStory();
    }


    //    for test
    public Script(String category, String story) {
        this.category = category;
        this.story = story;
    }
}