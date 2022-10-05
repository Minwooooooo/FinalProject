package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TESTcontroller {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //Test Login
    @RequestMapping(value = "/test/login",method = RequestMethod.GET)
    private String testLogin(){
        Member member = Member.builder()
                .id(123L)
                .memberName("나")
                .profileImage("없음")
                .userRole(Member.Authority.ROLE_USER)
                .build();
        memberRepository.save(member);
        String token=jwtTokenProvider.creatToken(member);
        return token;
    }

}
