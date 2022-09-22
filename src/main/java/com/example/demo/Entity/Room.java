package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String category;

    @Column
    private int memberCount;

    @Column
    private boolean lock;

    @Column
    private String roomPw;

    public enum categoryList{
        생활영어, 캠스터디, 시험대비, 스크립트
    }
}
