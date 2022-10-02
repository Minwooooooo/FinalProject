package com.example.demo.dto.requestDto;


import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowingRequestDto {
//    private Member member;
    private Long followingSubjectId;
    private Long SendFollowingUserId;
}
