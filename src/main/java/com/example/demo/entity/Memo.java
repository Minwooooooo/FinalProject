package com.example.demo.entity;

import com.example.demo.dto.requestDto.MemoRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Memo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Column
    private String contents;

    @Column
    private LocalDate createDate;

    @Column
    private LocalDate modifiedDate;

    @Column
    private String category;

    @Column
    private String roomName;


    public void updateMemo(String contents) {
        this.contents = contents;
        this.modifiedDate = LocalDate.now();
    }



}
