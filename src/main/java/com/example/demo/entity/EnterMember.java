package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EnterMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private ChatRoom chatRoom;

    @Column
    private String sessionId;

    @Column
    private String roomId;


    public EnterMember(String sessionId, String roomId) {
        this.sessionId = sessionId;
        this.roomId = roomId;
    }
}
