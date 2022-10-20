package com.example.demo.entity.chat;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String type;

    @Column
    private String senderId;

    @Column
    private String senderName;

    @Column
    private String message;

    @Column
    private String etc;

    @Column
    private Date date;

}
