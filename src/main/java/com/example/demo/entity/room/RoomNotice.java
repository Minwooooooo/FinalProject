package com.example.demo.entity.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class RoomNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ChatRoom chatRoom;

    @Column
    private String notice;

    public void editNotice(String temp_notice) {
        this.notice=temp_notice;
    }
}
