package com.example.demo.entity.room;

import com.example.demo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class RoomDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ChatRoom chatRoom;

    @OneToMany
    private List<Member> enterMembers;


    @ManyToMany
    private List<Member> blackMembers;

    @OneToOne
    private Member roomManager;

    public static RoomDetail create(ChatRoom chatRoom) {
        RoomDetail roomDetail = new RoomDetail();
        roomDetail.chatRoom=chatRoom;

        roomDetail.enterMembers=new ArrayList<Member>();
        roomDetail.blackMembers=new ArrayList<Member>();

        return roomDetail;
    }


    public void addMember(Member member) {
        enterMembers.add(member);
    }

    public void removeMember(Member member) {
        enterMembers.remove(member);
    }

    public void vanMember(Member member){
        blackMembers.add(member);
    }

    public void setManager(Member member) {
        this.roomManager=member;
    }
}
