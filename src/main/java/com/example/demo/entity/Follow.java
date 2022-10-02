package com.example.demo.entity;

import com.example.demo.dto.requestDto.FollowingRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Subject
//    Able to search as list on One Member
    @OneToOne
    private Member member;

//    Encounter Member
//    Able to redirect to FollowId
    @Column
    private Long followingMemberId;

}
