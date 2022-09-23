package com.example.demo.controller;

import com.example.demo.dto.request.AccountRequestDto;
import com.example.demo.global.dto.ResponseDto;
import com.example.demo.service.AccountService;
import com.example.demo.service.kakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
//    private final JwtUtil jwtUtil;
    private final AccountService accountService;
    private final kakaoService kakaoService;

    @PostMapping("/account/signup")
    public ResponseDto<?> signup(@RequestBody AccountRequestDto ReqDto){
        return accountService.signup(ReqDto);
    }

}
