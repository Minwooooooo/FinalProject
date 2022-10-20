package com.example.demo.service.Member;

import com.example.demo.entity.member.Member;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


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

        Member member = memberRepository.findById(memberId).get();

        return member;
    }
}
