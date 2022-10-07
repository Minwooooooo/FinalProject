package com.example.demo.entity.util;

import com.example.demo.entity.member.Member;
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
public class StudyTimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    private Member member;

    @Column
    private String category;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String time;

    public void editTime(String studyTime) {
        this.time=studyTime;
    }
}
