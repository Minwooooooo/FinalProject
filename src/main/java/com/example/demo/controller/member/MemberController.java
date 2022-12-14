package com.example.demo.controller.member;

import com.example.demo.dto.ResponseDto;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.Member.KakaoLoginService;
import com.example.demo.service.Member.NaverLoginService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;
    @GetMapping(value = "/login/kakao")
    public ResponseDto<?> kakaoLogin(@RequestParam String code, HttpServletResponse httpServletResponse, HttpServletRequest request) throws IOException, ParseException {
        //E001: https로 로그인시 오류
        return kakaoLoginService.kakaoLogin(code,httpServletResponse,request);
    }

    @GetMapping(value = "/login/naver")
    public ResponseDto<?> naverLogin(@RequestParam String code,@RequestParam String state,
                                     HttpServletResponse httpServletResponse, HttpServletRequest request){
        return naverLoginService.naverLogin(code,state,httpServletResponse,request);
    }



}
