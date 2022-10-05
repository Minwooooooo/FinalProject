package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //토큰으로 멤버찾기
    public Member findMember(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        Claims claim = jwtTokenProvider.tempClaim(token);

        String memberId = claim.getSubject();

        Long id = Long.valueOf(memberId);

        Member member = memberRepository.findById(id).get();

        return member;
    }
}
