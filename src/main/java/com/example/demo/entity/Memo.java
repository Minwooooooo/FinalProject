package com.example.demo.entity;

import com.example.demo.dto.requestDto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Memo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @Column
    private String roomName;

    @Column
    private String content;

    @Column
    private String category;


    public Memo(MemoRequestDto dto) {
        this.roomName = dto.getRoomName();
        this.content = dto.getContent();
        this.category = dto.getCategory();
        super.getCreatedAt();
    }

    public void update(String content){
        this.content= content;
    }
}